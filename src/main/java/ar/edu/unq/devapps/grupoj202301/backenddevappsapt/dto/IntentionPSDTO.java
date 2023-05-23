package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Data
@Component
@AllArgsConstructor
@RequiredArgsConstructor
public class IntentionPSDTO {
    @NotBlank
    private String userEmail;
    @NotBlank
    private String cryptoCoinName;
    @NotNull
    private BigDecimal amountOfCryptoCoin;
    @NotNull
    private BigDecimal valueInDollars;
    @NotNull
    private TransactionType transactionType;

    public IntentionPSDTO(String userEmail, String cryptoCoinName, String amountOfCryptoCoin, String valueInDollars, String transactionType) {
        this.userEmail = userEmail;
        this.cryptoCoinName = cryptoCoinName;
        this.amountOfCryptoCoin = new BigDecimal(amountOfCryptoCoin);
        this.valueInDollars = new BigDecimal(valueInDollars);
        this.transactionType = TransactionType.valueOf(transactionType);
    }
}
