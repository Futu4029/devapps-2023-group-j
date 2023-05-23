package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.impl;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.CryptoCoinPersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoCoinService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.exception.ExternalAPIException;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CryptoCoinServiceImpl implements CryptoCoinService {

    @Autowired
    private CryptoCoinPersistence cryptoCoinPersistence;

    public List<CryptoCoin> getAllQuotations() {
        return cryptoCoinPersistence.findAll();
    }

    public void saveAllCryptoCoins(List<CryptoCoin> cryptoCoinList) { cryptoCoinPersistence.saveAll(cryptoCoinList); }

    public BigDecimal getTheValueOfAnAmountOfCryptoCoinInPesos(String name, BigDecimal amount) throws IOException {
        CryptoCoin cryptoCoin = cryptoCoinPersistence.findByName(name);
        return cryptoCoin.getQuotation().multiply(amount).multiply(getPesosValueByDollar());
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

    public BigDecimal getPesosValueByDollar() throws IOException {
        Response response = genericQueryToAnExternalApi("https://www.dolarsi.com/api/api.php?type=valoresprincipales");
        ResponseBody responseBody = response.body();
        if (response.isSuccessful() && responseBody != null) {
            JSONArray jsonArray = new JSONArray(responseBody.string());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject node = jsonArray.getJSONObject(i).getJSONObject("casa");
                if (node.getString("nombre").equals("Dolar Oficial")) {
                    String result = node.getString("venta").replace(",", ".");
                    return new BigDecimal(result);
                }
            }
        }
        throw new ExternalAPIException("Could not obtain dolarSi resource");
    }

    @Override
    public BigDecimal getQuotationByName(String name) {
        return cryptoCoinPersistence.getQuotationByName(name);
    }

    private Response genericQueryToAnExternalApi(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        return client.newCall(request).execute();
    }
}
