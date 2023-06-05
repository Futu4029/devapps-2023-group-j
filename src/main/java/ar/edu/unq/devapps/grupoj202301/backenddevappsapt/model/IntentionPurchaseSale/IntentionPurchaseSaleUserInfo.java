package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class IntentionPurchaseSaleUserInfo {
    private String name;
    private String surname;
    private String email;
    private int pointsObtained;
    private int operationsPerformed;
    private List<IntentionPurchaseSaleSummarized> intentionPurchaseSaleSummarizedList;
}
