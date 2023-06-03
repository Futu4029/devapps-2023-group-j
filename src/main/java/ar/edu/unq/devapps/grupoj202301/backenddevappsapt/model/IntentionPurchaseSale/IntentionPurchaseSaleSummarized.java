package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.flags.IntentionType;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@Component
@AllArgsConstructor
@RequiredArgsConstructor
public class IntentionPurchaseSaleSummarized extends IntentionPurchaseSaleCoreData {

    @Column(nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    @Column(nullable = false)
    @NotNull
    private BigDecimal pesosAmount;

    public IntentionPurchaseSaleSummarized(String email, String cryptoCoinName, BigDecimal amountOfCryptoCoin, BigDecimal quotationBase, BigDecimal pesosAmount, IntentionType intentionType) {
        super(email, cryptoCoinName, amountOfCryptoCoin.toString(), quotationBase.toString(), intentionType.toString());
        this.pesosAmount = pesosAmount;
    }
}
