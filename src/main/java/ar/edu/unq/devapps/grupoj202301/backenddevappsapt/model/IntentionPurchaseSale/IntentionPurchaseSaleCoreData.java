package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.flags.IntentionType;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.anotation.LettersOnlyAdmits;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@MappedSuperclass
@Data
@Component
@AllArgsConstructor
@RequiredArgsConstructor
public class IntentionPurchaseSaleCoreData {
    @Column(nullable = false)
    @NotBlank
    @LettersOnlyAdmits
    private String cryptoCoinName;

    @Column(nullable = false)
    @NotNull
    private BigDecimal amountOfCryptoCoin;

    @Column(nullable = false)
    @NotNull
    private BigDecimal quotationBase;

    @Column(nullable = false)
    @NotNull
    private IntentionType intentionType;

    @Email
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;

    public IntentionPurchaseSaleCoreData(String email, String cryptoCoinName, String amountOfCryptoCoin, String quotationBase, String intentionType) {
        this.email = email;
        this.cryptoCoinName = cryptoCoinName;
        this.amountOfCryptoCoin = new BigDecimal(amountOfCryptoCoin);
        this.quotationBase = new BigDecimal(quotationBase);
        this.intentionType = IntentionType.valueOf(intentionType);
    }
}

