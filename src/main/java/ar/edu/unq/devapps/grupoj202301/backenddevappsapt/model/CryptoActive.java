package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Entity
@Table(name="crypto_active")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CryptoActive {

    @Id
    public String name;
    @ManyToOne()
    @JoinColumn(name = "coin_name", referencedColumnName = "name")
    public CryptoCoin coin;
    public BigDecimal value;
    @ManyToOne()
    @JoinColumn(name = "coin_address", referencedColumnName = "address")
    public DigitalWallet walletAddress;
}
