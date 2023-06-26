package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class IntentionPurchaseSaleVolumeInfo {
    private BigDecimal totalDollarAmount = new BigDecimal("0");
    private BigDecimal totalPesosAmount = new BigDecimal("0");
    List<IntentionPurchaseSale> intentionPurchaseSaleResultList = new ArrayList<>();

    public IntentionPurchaseSaleVolumeInfo(BigDecimal totalDollarAmount, BigDecimal totalPesosAmount, List<IntentionPurchaseSale> intentionPurchaseSaleResultList) {
        this.totalDollarAmount = totalDollarAmount;
        this.totalPesosAmount = totalPesosAmount;
        this.intentionPurchaseSaleResultList = intentionPurchaseSaleResultList;
    }
}
