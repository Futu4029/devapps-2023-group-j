package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webservice;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.IntentionPSDTO;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.TransactionDataDTO;
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
import java.util.ArrayList;
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

    @PostMapping("/interactWithATransactionRequest")
    @ResponseBody
    public ResponseEntity<String> interactWithATransactionRequest(@Valid @RequestBody TransactionDataDTO transactionDataDTO) {
        ArrayList<Object> result = getInformationFrom(transactionDataDTO);
        User user = (User) result.get(0);
        TransactionRequest transactionRequest = (TransactionRequest) result.get(1);
        return ResponseEntity.ok(transactionRequestService.interactWithATransactionRequest(user, transactionRequest));
    }

    @PostMapping("/confirmTransaction")
    @ResponseBody
    public ResponseEntity<String> confirmReception(@Valid @RequestBody TransactionDataDTO transactionDataDTO) {
        ArrayList<Object> result = getInformationFrom(transactionDataDTO);
        User user = (User) result.get(0);
        TransactionRequest transactionRequest = (TransactionRequest) result.get(1);
        transactionRequestService.checkPriceDifference(transactionDataDTO);
        userService.confirmReception(user, transactionRequest);
        return ResponseEntity.ok(transactionRequestService.updateStatus(transactionRequest, TransactionState.ACCEPTED));
    }

    @PostMapping("/cancel")
    @ResponseBody
    public ResponseEntity<String> cancelTheRequest(@Valid @RequestBody TransactionDataDTO transactionDataDTO) {
        ArrayList<Object> result = getInformationFrom(transactionDataDTO);
        User user = (User) result.get(0);
        TransactionRequest transactionRequest = (TransactionRequest) result.get(1);
        userService.discountReputation(user, transactionRequest);
        return ResponseEntity.ok(transactionRequestService.cancelIfYouAreTheOwner(user, transactionRequest));
    }

    @GetMapping("/betweenDates/{email}/{startDate}/{endDate}")
    @ResponseBody
    private ResponseEntity<TransactionRequestVolumeInfo> volumeOperatedBetweenDates(@PathVariable("email") String email, @PathVariable("startDate") LocalDateTime startDate, @PathVariable("endDate") LocalDateTime endDate) throws IOException {
        return ResponseEntity.ok(transactionRequestService.volumeOperatedBetweenDates(email, startDate, endDate));
    }

    @GetMapping("/getActivesTransactions/{email}")
    @ResponseBody
    private ResponseEntity<List<TransactionRequest>> getActivesTransactions(@PathVariable("email") String email) throws IOException {
        return ResponseEntity.ok(transactionRequestService.getTransactionsByState(email, TransactionState.ACTIVE));
    }

    public ArrayList<Object> getInformationFrom(@Valid @RequestBody TransactionDataDTO transactionDataDTO) {
        User user = userService.getUserByEmail(transactionDataDTO.getEmail());
        TransactionRequest transactionRequest = transactionRequestService.getTransactionsById(transactionDataDTO.getTransactionId());
        ArrayList<Object> result = new ArrayList<>();
        result.add(user);
        result.add(transactionRequest);
        return  result;
    }
}
