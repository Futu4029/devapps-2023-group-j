package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.anotation.LettersAndNumbersOnlyAdmits;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name="digital_wallet")
@NoArgsConstructor
public class DigitalWallet {

    @Id
    @Column(nullable = false, unique = true)
    @Size(min=8, max=8)
    @LettersAndNumbersOnlyAdmits
    @NotBlank
    private String address;

    @NotBlank
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    @NotNull
    private List<CryptoActive> cryptoCoinsAcquired = new ArrayList<>();

    public DigitalWallet(String address, String email) {
        this.address = address;
        this.email = email;
    }

    public boolean getCryptoActiveIfPossibleToSell(String cryptoCoinName, BigDecimal amountOfCryptoCoin) {
        return  cryptoCoinsAcquired.stream().anyMatch((cryptoActive) ->
                    Objects.equals(cryptoActive.getCryptoCoinName(), cryptoCoinName) &&
                    cryptoActive.getAmountOfCryptoCoin().compareTo(amountOfCryptoCoin) >= 0
        );
    }
}
