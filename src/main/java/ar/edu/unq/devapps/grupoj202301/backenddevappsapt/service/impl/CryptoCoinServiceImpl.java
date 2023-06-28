package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.impl;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.CryptoCoinDTO;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.CryptoCoinPersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoCoinService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.Aspects;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.exception.ExternalAPIException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class CryptoCoinServiceImpl implements CryptoCoinService {
    private static final Logger logger = LoggerFactory.getLogger(Aspects.class);

    @Autowired
    private CryptoCoinPersistence cryptoCoinPersistence;

    @Autowired
    private CacheManager cacheManager;

    @Override
    public boolean elementIsPresent(CryptoCoin cryptoCoin) {
        return cryptoCoinPersistence.existsById(cryptoCoin.getName());
    }

    @Override
    public String registerElement(CryptoCoin cryptoCoin) {
        CryptoCoin cryptoCoinResult = cryptoCoinPersistence.save(cryptoCoin);
        try {
            cacheManager.getCache("cryptoCoinCache").put(cryptoCoinResult.getName(), cryptoCoinResult);
        } catch (Exception exception) {
            throw new NoSuchElementException("ERROR: Cache cryptoCoinCache no such");
        }
        return cryptoCoinResult.getName();
    }

    @Override
    public void updateElement(CryptoCoin cryptoCoin) {
        if(elementIsPresent(cryptoCoin)) {
            CryptoCoin cryptoCoinResult = cryptoCoinPersistence.save(cryptoCoin);
            try {
                cacheManager.getCache("cryptoCoinCache").put(cryptoCoinResult.getName(), cryptoCoinResult);
            } catch (Exception exception) {
                throw new NoSuchElementException("ERROR: Cache cryptoCoinCache no such");
            }
        }
    }

    @Override
    public Optional<CryptoCoin> findElementById(String elementId) {
        return cryptoCoinPersistence.findById(elementId);
    }

    private CryptoCoin getCryptocoin(String elementId) {
        CryptoCoin cryptoCoin;
        try {
            cryptoCoin = (CryptoCoin) cacheManager.getCache("cryptoCoinCache").get(elementId).get();
            logger.info(elementId + " - " + "Retrivered from cache");
        } catch (Exception exception) {
            logger.info(elementId + " - " + "Retrivered from BDD");
            cryptoCoin = cryptoCoinPersistence.findById(elementId).get();
            cacheManager.getCache("cryptoCoinCache").put(cryptoCoin.getName(), cryptoCoin);
        }
        return cryptoCoin;
    }

    @Override
    public CryptoCoin findCryptoCoinWithQuotationByDatesWithin24Hours(String cryptoCoinName) {
        LocalDateTime now = LocalDateTime.now();
        return cryptoCoinPersistence.findCryptoCoinWithQuotationByDatesWithin24Hours(cryptoCoinName,now.minusHours(24),now);
    }

    @Override
    public BigDecimal getExternalQuotationByName(String cryptoCoinName) throws IOException {
            Response response = genericQueryToAnExternalApi("https://api.binance.us/api/v3/ticker/price?symbol=" + cryptoCoinName);
            ResponseBody responseBody = response.body();

            if (response.isSuccessful() && responseBody != null) {
                JSONObject result = new JSONObject(responseBody.string());
                if (result.has("price")) {
                    return new BigDecimal(result.getString("price"));
                }
            }

            response = genericQueryToAnExternalApi("https://api.binance.com/api/v3/ticker/price?symbol=" + cryptoCoinName);
            responseBody = response.body();

            if (response.isSuccessful() && responseBody != null) {
                JSONObject result = new JSONObject(responseBody.string());
                if (result.has("price")) {
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
        List<String> cryptoCoinNames = cryptoCoinPersistence.getAllCryptoCoinNames();
        List<CryptoCoinDTO> responseList = new ArrayList<>();
        for(String cryptoCoinName : cryptoCoinNames){
            CryptoCoin c = this.getCryptocoin(cryptoCoinName);
            responseList.add(new CryptoCoinDTO(c.getName(), c.getQuotationByDates().get(0)));
        }
        return responseList;
    }


    private Response genericQueryToAnExternalApi(String url) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(5, TimeUnit.MINUTES)
                .readTimeout(10, TimeUnit.SECONDS)
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