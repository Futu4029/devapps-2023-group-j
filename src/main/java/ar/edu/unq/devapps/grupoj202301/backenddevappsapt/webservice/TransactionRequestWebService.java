package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webservice;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.IntentionPSDTO;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.TransactionRequestVolumeInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.*;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionState;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.*;
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
    private UserService userService;
    @Autowired
    private CryptoActiveService cryptoActiveService;
    @Autowired
    private TransactionRequestService transactionRequestService;

    @PostMapping("/createIntentionPS")
    @ResponseBody
    public ResponseEntity<String> createIntentionPurchaseSale(@Valid @RequestBody IntentionPSDTO intention) throws IOException {
        User user = userService.getUserByEmail(intention.getUserEmail());
        CryptoActive cryptoActive = cryptoActiveService.save(new CryptoActive(intention.getCryptoCoinName(), intention.getAmountOfCryptoCoin()));
        return ResponseEntity.ok(transactionRequestService.createIntentionPurchaseSale(user, cryptoActive, intention.getTransactionType()));
    }

    @GetMapping("/betweenDates/{email}/{startDate}/{endDate}")
    @ResponseBody
    private ResponseEntity<TransactionRequestVolumeInfo> volumeOperatedBetweenDates(@PathVariable("email") String email, @PathVariable("startDate") LocalDateTime startDate, @PathVariable("endDate") LocalDateTime endDate) throws IOException {
        return ResponseEntity.ok(transactionRequestService.volumeOperatedBetweenDates(email, startDate, endDate));
    }

    @GetMapping("/getActivesTransactions")
    @ResponseBody
    private ResponseEntity<List<TransactionRequest>> getActivesTransactions() throws IOException {
        return ResponseEntity.ok(transactionRequestService.getTransactionsByState(TransactionState.ACTIVE));
    }
}
