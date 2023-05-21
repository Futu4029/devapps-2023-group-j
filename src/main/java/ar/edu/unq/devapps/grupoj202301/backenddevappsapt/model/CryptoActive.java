package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;

@Data
@Entity
@Table(name="crypto_active")
@AllArgsConstructor
@RequiredArgsConstructor
public class CryptoActive {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotBlank
    private String cryptoCoinName;

    @Column(nullable = false, precision = 38)
    @NotNull
    private BigDecimal amountOfCryptoCoin;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "wallet_address", referencedColumnName = "address")
    private DigitalWallet digitalWallet;

    public CryptoActive(String cryptoCoinName, DigitalWallet digitalWallet, BigDecimal amountOfCryptoCoin) {
        this.cryptoCoinName = cryptoCoinName;
        this.digitalWallet = digitalWallet;
        this.amountOfCryptoCoin = amountOfCryptoCoin;
    }
}