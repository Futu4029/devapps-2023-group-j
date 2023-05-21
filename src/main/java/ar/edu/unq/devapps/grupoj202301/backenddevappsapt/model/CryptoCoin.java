package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @Column(nullable = false, unique = true)
    @NotBlank
    private String name;

    @Column(nullable = false, precision = 38, scale = 8)
    @NotNull
    private BigDecimal quotation;

    @Column(nullable = false)
    private LocalDateTime quotationDate = LocalDateTime.now();

    public CryptoCoin(String name, BigDecimal quotation) {
        this.name = name;
        this.quotation = quotation;
    }
}
