package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.flags.IntentionType;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.flags.StatusType;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Entity
@Table(name="intent_to_purchase_sale")
@AllArgsConstructor
@NoArgsConstructor
public class IntentionPurchaseSale extends IntentionPurchaseSaleSummarized {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String surname;

    @Column(nullable = false)
    private StatusType statusType = StatusType.ACTIVE;

    @Column(nullable = false)
    private String destiny;

    @Email
    private String anotherUserEmail = null;

    public IntentionPurchaseSale(User user, String cryptoCoinName, BigDecimal amountOfCryptoCoin, BigDecimal quotationBase, BigDecimal pesosAmount, IntentionType intentionType) {
        super(user.getEmail(), cryptoCoinName, amountOfCryptoCoin, quotationBase, pesosAmount, intentionType);
        this.surname = user.getSurname();
        this.name = user.getName();
        this.destiny = (intentionType.equals(IntentionType.PURCHASE)) ? user.getWalletAddress() : user.getCvu();
    }
}
