package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.exception.ExternalAPIException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;

public class BCRAData {
    private static final String accessToken = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MTYyMTcyMjgsInR5cGUiOiJleHRlcm5hbCIsInVzZXIiOiJjb250YXJkby5qdWFucGFibG9AZ21haWwuY29tIn0.17L8S16w2-LS04LvWC4EIMQK6OlcJVLqANWgl_eTvyXCBWcZi0JWjbXTzfbXSu-TO5nGd0Ah93VasjoKeP_C6w";
    private static String urlBase = "https://api.estadisticasbcra.com/";

    public static BigDecimal getDollarValue() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlBase + "/usd_of")
                .header("Authorization", "Bearer " + accessToken)
                .build();
        Response response = client.newCall(request).execute();
        ResponseBody responseBody = response.body();

        if (response.isSuccessful() && responseBody != null) {
            JSONArray jsonArray = new JSONArray(responseBody.string());
            JSONObject lastElement = jsonArray.getJSONObject(jsonArray.length() - 1);
            return lastElement.getBigDecimal("v");
        } else {
            throw new ExternalAPIException("Could not obtain central bank resource");
        }
    }
}
