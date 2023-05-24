package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
@RequiredArgsConstructor
public class TransactionDataDTO {
    private String email;
    private Long transactionId;
}
