package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionState;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @JoinColumn(name = "crypto_active_id")
    @OneToOne(cascade = CascadeType.MERGE)
    @NotNull
    private CryptoActive cryptoActive;
    @Column(nullable = false)
    @NotNull
    private BigDecimal amount;
    @Column(nullable = false)
    @NotNull
    private LocalDateTime date;
    @Column(nullable = false)
    private TransactionState transactionState = TransactionState.ACTIVE;
    @Column(nullable = false)
    @NotNull
    private BigDecimal pesosAmount;
    @Column(nullable = false)
    @NotNull
    private BigDecimal dollarAmount;
    @Column(nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();
    @OneToOne
    @NotNull
    private User userOwner;
    @Column(nullable = false)
    @NotNull
    private TransactionType transactionType;

    public TransactionRequest(CryptoActive cryptoActive,BigDecimal amount, LocalDateTime date, User user, BigDecimal dollarAmount, BigDecimal pesosAmount, TransactionType transactionType) {
        this.cryptoActive = cryptoActive;
        this.amount = amount;
        this.date = date;
        this.userOwner = user;
        this.dollarAmount = dollarAmount;
        this.pesosAmount = pesosAmount;
        this.transactionType = transactionType;
    }

}
