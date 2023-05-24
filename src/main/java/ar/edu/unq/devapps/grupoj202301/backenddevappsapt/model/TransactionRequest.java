package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.ActionType;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionState;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionType;
import jakarta.persistence.*;
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
    @JoinColumn(name = "crypto_active_id", referencedColumnName = "id")
    @OneToOne
    @NotNull
    private CryptoActive cryptoActive;
    @Column(nullable = false)
    @NotNull
    private LocalDateTime date;
    @Column(nullable = false)
    private TransactionState transactionState = TransactionState.ACTIVE;
    @Column(nullable = false, precision = 38, scale = 8)
    @NotNull
    private BigDecimal quotation;
    @Column(nullable = false, precision = 38, scale = 8)
    @NotNull
    private BigDecimal pesosAmount;
    @Column(nullable = false)
    @NotNull
    private BigDecimal dollarAmount;
    @Column(nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "user_owner_email", referencedColumnName = "email")
    @NotNull
    private User userOwner;

    @OneToOne
    @JoinColumn(name = "user_secondary_email", referencedColumnName = "email")
    private User userSecondary;

    @Column(nullable = false)
    @NotNull
    private TransactionType transactionType;

    @Column
    private ActionType actionType;

    public TransactionRequest(CryptoActive cryptoActive, LocalDateTime date, User user, BigDecimal quotation, BigDecimal dollarAmount, BigDecimal pesosAmount, TransactionType transactionType) {
        this.cryptoActive = cryptoActive;
        this.date = date;
        this.userOwner = user;
        this.quotation = quotation;
        this.dollarAmount = dollarAmount;
        this.pesosAmount = pesosAmount;
        this.transactionType = transactionType;
    }

    public boolean isPurchase() {
        return transactionType.equals(TransactionType.PURCHASE);
    }

    public boolean isSell() {
        return transactionType.equals(TransactionType.SELL);
    }
}
