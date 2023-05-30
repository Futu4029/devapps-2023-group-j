package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.impl;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.crypto.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.crypto.CryptoCoinDTO;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.CryptoCoinPersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoCoinService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.exception.ExternalAPIException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoCoinServiceImpl implements CryptoCoinService {

    @Autowired
    private CryptoCoinPersistence cryptoCoinPersistence;

    @Override
    public boolean elementIsPresent(CryptoCoin cryptoCoin) {
        return cryptoCoinPersistence.existsById(cryptoCoin.getName());
    }

    @Override
    public String registerElement(CryptoCoin element) {
        CryptoCoin cryptoCoin = cryptoCoinPersistence.save(element);
        return cryptoCoin.getName();
    }

    @Override
    public CryptoCoinDTO findCryptoCoinWithQuotationByDatesWithin24Hours(String cryptoCoinName) {
        LocalDateTime startDate = LocalDateTime.now().minusHours(24);
        CryptoCoin cryptoCoin = cryptoCoinPersistence.findCryptoCoinWithQuotationByDatesWithin24Hours(cryptoCoinName, startDate);
        return new CryptoCoinDTO(cryptoCoin.getName(), cryptoCoin.getQuotationByDates());
    }

    public BigDecimal getCryptoCoinCotizationByName(String cryptoCoinName) throws IOException {
        Response response = genericQueryToAnExternalApi("https://api.binance.com/api/v3/ticker/price?symbol=" + cryptoCoinName);
        ResponseBody responseBody = response.body();
        if (response.isSuccessful() && responseBody != null) {
            String result = new JSONObject(responseBody.string()).getString("price");
            return new BigDecimal(result);
        }
        throw new ExternalAPIException("Could not obtain Binance resource");
    }

    private Response genericQueryToAnExternalApi(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        return client.newCall(request).execute();
    }
}