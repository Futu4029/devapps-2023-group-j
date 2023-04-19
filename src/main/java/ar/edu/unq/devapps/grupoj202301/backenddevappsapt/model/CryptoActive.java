package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model;

import jakarta.persistence.*;
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
    public CryptoCoin coin;

    @ManyToOne()
    @JoinColumn(name = "wallet_address", referencedColumnName = "address")
    public DigitalWallet digitalWallet;

    public BigDecimal value;

    public CryptoActive(String name, DigitalWallet digitalWallet, String value) {
        this.coin = new CryptoCoin(name,"0");
        this.digitalWallet = digitalWallet;
        this.value = new BigDecimal(value);
    }
}
