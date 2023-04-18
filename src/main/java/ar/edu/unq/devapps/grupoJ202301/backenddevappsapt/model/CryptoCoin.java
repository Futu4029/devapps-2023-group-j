package ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name="crypto_coin")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CryptoCoin {

    @Id
    private String name;
    @Column(nullable = false)
    private BigDecimal value;
    @Column(nullable = false)
    private Date quotationDate;
}
