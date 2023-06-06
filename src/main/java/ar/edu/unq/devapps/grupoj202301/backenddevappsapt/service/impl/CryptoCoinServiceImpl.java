package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.impl;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.CryptoCoinDTO;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.QuotationByDate;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.CryptoCoinPersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoCoinService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.exception.ExternalAPIException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CryptoCoinServiceImpl implements CryptoCoinService {

    @Autowired
    private CryptoCoinPersistence cryptoCoinPersistence;

    @Override
    public boolean elementIsPresent(CryptoCoin cryptoCoin) {
        return cryptoCoinPersistence.existsById(cryptoCoin.getName());
    }

    @Override
    public String registerElement(CryptoCoin cryptoCoin) {
        CryptoCoin cryptoCoinResult = cryptoCoinPersistence.save(cryptoCoin);
        return cryptoCoinResult.getName();
    }

    @Override
    public void updateElement(CryptoCoin cryptoCoin) {
        if(elementIsPresent(cryptoCoin)) {
            cryptoCoinPersistence.save(cryptoCoin);
        }
    }

    @Override
    public Optional<CryptoCoin> findElementById(String elementId) {
        return cryptoCoinPersistence.findById(elementId);
    }

    @Override
    public CryptoCoin findCryptoCoinWithQuotationByDatesWithin24Hours(String cryptoCoinName) {
        LocalDateTime now = LocalDateTime.now();
        CryptoCoin cryptoCoin = cryptoCoinPersistence.findCryptoCoinWithQuotationByDatesWithin24Hours(cryptoCoinName,now.minusHours(24),now);
        return cryptoCoin;
    }

    @Override
    public BigDecimal getExternalQuotationByName(String cryptoCoinName) throws IOException {
        Response response = genericQueryToAnExternalApi("https://api.binance.us/api/v3/ticker/price?symbol=" + cryptoCoinName);
        ResponseBody responseBody = response.body();

        if (response.isSuccessful() && responseBody != null) {
            JSONObject result = new JSONObject(responseBody.string());
                if(result.has("price")) {
                    return new BigDecimal(result.getString("price"));
                }
        }

        response = genericQueryToAnExternalApi("https://api.binance.com/api/v3/ticker/price?symbol=" + cryptoCoinName);
        responseBody = response.body();

        if (response.isSuccessful() && responseBody != null) {
            JSONObject result = new JSONObject(responseBody.string());
            if(result.has("price")) {
                return new BigDecimal(result.getString("price"));
            }
        }

        throw new ExternalAPIException("Could not obtain Binance resource");
    }

    @Override
    public BigDecimal getThePriceOfThePurchaseDollar() throws IOException {
        return getPesosValueByDollar("compra");
    }

    @Override
    public BigDecimal getThePriceOfTheSellDollar() throws IOException {
        return getPesosValueByDollar("venta");
    }

    @Override
    public List<CryptoCoinDTO> getCryptoCoinsQuotations() {
        List<CryptoCoin> cryptoCoinList = cryptoCoinPersistence.getCryptoCoinsQuotations();
        List<CryptoCoinDTO> responseList = new ArrayList<>();
        for(CryptoCoin c : cryptoCoinList){
            responseList.add(new CryptoCoinDTO(c.getName(), c.getQuotationByDates().get(0)));
        }
        return responseList;
    }


    private Response genericQueryToAnExternalApi(String url) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder().url(url).build();
        return client.newCall(request).execute();
    }

    private BigDecimal getPesosValueByDollar(String type) throws IOException {
        Response response = genericQueryToAnExternalApi("https://www.dolarsi.com/api/api.php?type=valoresprincipales");
        ResponseBody responseBody = response.body();
        if (response.isSuccessful() && responseBody != null) {
            JSONArray jsonArray = new JSONArray(responseBody.string());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject node = jsonArray.getJSONObject(i).getJSONObject("casa");
                if (node.getString("nombre").equals("Dolar Oficial")) {
                    String result = node.getString(type).replace(",", ".");
                    return new BigDecimal(result);
                }
            }
        }
        throw new ExternalAPIException("Could not obtain dolarSi resource");
    }
}