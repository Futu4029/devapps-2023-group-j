package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.crypto.CryptoCoin;
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

    @Column(nullable = false, precision = 38, scale = 2)
    private BigDecimal price;

    public CryptoActive(String name, String price) {
        this.coin = new CryptoCoin(name);
        this.price = new BigDecimal(price);
    }
}
