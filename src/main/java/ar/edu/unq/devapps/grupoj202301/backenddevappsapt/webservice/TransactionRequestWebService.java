package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webservice;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.IntentionPSDTO;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.TransactionDataDTO;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.TransactionRequestVolumeInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.TransactionRequest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionState;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.TransactionRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@ControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionRequestWebService {

    @Autowired
    private TransactionRequestService transactionRequestService;

    @PostMapping("/createIntentionPS")
    @ResponseBody
    public ResponseEntity<String> createIntentionPurchaseSale(@Valid @RequestBody IntentionPSDTO intention) throws IOException {
        return ResponseEntity.ok(transactionRequestService.createIntentionPurchaseSale(intention));
    }

    @PostMapping("/interactWithATransactionRequest")
    @ResponseBody
    public ResponseEntity<String> interactWithATransactionRequest(@Valid @RequestBody TransactionDataDTO transactionDataDTO) {
        return ResponseEntity.ok(transactionRequestService.interactWithATransactionRequest(transactionDataDTO));
    }

    @PostMapping("/confirmTransaction")
    @ResponseBody
    public ResponseEntity<String> confirmReception(@Valid @RequestBody TransactionDataDTO transactionDataDTO) {
        return ResponseEntity.ok(transactionRequestService.confirmReception(transactionDataDTO));
    }

    @PostMapping("/cancel")
    @ResponseBody
    public ResponseEntity<String> cancelTheRequest(@Valid @RequestBody TransactionDataDTO transactionDataDTO) {
        return ResponseEntity.ok(transactionRequestService.cancelTransactionRequest(transactionDataDTO));
    }

    @GetMapping("/betweenDates/{email}/{startDate}/{endDate}")
    @ResponseBody
    public ResponseEntity<TransactionRequestVolumeInfo> volumeOperatedBetweenDates(@PathVariable("email") String email, @PathVariable("startDate") LocalDateTime startDate, @PathVariable("endDate") LocalDateTime endDate) throws IOException {
        return ResponseEntity.ok(transactionRequestService.volumeOperatedBetweenDates(email, startDate, endDate));
    }

    @GetMapping("/getActivesTransactions/{email}")
    @ResponseBody
    public ResponseEntity<List<TransactionRequest>> getActivesTransactions(@PathVariable("email") String email) throws IOException {
        return ResponseEntity.ok(transactionRequestService.getTransactionsByState(email, TransactionState.ACTIVE));
    }


}
