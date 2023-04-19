package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name="transaction_data")
@NoArgsConstructor
public class TransactionData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(nullable = false)
    @NotBlank
    private String cryptoName;
    @Column(nullable = false)
    @NotNull
    private BigDecimal cryptoAmount;
    @Column(nullable = false)
    @NotNull
    private BigDecimal pesosAmount;
    @Column(nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();
    @PrimaryKeyJoinColumn
    @OneToOne
    @NotNull
    private User userOwner;
    @Column(nullable = false)
    @NotNull
    private TransactionType transactionType;

    public TransactionData(long id, String cryptoName, String cryptoAmount, String pesosAmount, User user, TransactionType transactionType) {
        this.id = id;
        this.cryptoName = cryptoName;
        this.cryptoAmount = new BigDecimal(cryptoAmount);
        this.pesosAmount = new BigDecimal(pesosAmount);
        this.userOwner = user;
        this.transactionType = transactionType;
    }
}
