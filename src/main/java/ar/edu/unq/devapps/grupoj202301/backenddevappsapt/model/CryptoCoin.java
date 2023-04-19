package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="crypto_coin")
@AllArgsConstructor
@RequiredArgsConstructor
public class CryptoCoin {

    @Id
    private String name;
    @Column(nullable = false)
    private BigDecimal value;
    @Column(nullable = false)
    private LocalDateTime quotationDate = LocalDateTime.now();

    public CryptoCoin(String name, String value) {
        this.name = name;
        this.value = new BigDecimal(value);
    }
}
