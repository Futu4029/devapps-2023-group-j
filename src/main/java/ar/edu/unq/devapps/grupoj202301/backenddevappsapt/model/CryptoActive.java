package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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

    @ManyToOne()
    @JoinColumn(name = "coin_name", referencedColumnName = "name")
    @NotEmpty
    private CryptoCoin coin;

    @ManyToOne()
    @JoinColumn(name = "wallet_address", referencedColumnName = "address")
    private DigitalWallet digitalWallet;

    @Column(nullable = false, precision = 38, scale = 2)
    private BigDecimal price;

    public CryptoActive(String name, DigitalWallet digitalWallet, String price) {
        this.coin = new CryptoCoin(name,"0");
        this.digitalWallet = digitalWallet;
        this.price = new BigDecimal(price);
    }
}
