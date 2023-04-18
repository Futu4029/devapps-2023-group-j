package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enumModel.OperationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name="transaction_request")
@NoArgsConstructor
public class TransactionRequest {

    @Id
    private long id;
    private String cryptoName;
    private BigDecimal cryptoAmount;
    private BigDecimal pesosAmount;
    private Date creationDate;
    private String email;
    private OperationType operationType;


}
