package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "quotation_by_date")
@AllArgsConstructor
@RequiredArgsConstructor
public class QuotationByDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private BigDecimal quotation;

    @Column(nullable = false)
    private LocalDateTime date;

    public QuotationByDate(BigDecimal quotation) {
        this.quotation = quotation;
        this.date = LocalDateTime.now();
    }
}
