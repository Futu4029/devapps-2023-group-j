package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="transaction")
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @PrimaryKeyJoinColumn
    @OneToOne
    private CryptoActive cryptoActive;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    private LocalDateTime date;
    @PrimaryKeyJoinColumn
    @OneToOne
    private TransactionData transactionData;

    public TransactionRequest(CryptoActive cryptoActive, String amount, LocalDateTime date, TransactionData transactionData) {
        this.cryptoActive = cryptoActive;
        this.amount = new BigDecimal(amount);
        this.date = date;
        this.transactionData = transactionData;
    }
}