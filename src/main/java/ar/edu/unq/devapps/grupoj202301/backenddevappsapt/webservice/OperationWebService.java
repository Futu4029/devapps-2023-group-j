package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webservice;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.IntentionPSDTO;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.*;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@ControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/operations")
@SuppressWarnings("unused")
public class OperationWebService {

    @Autowired
    private OperationService operationService;

    @Autowired
    private UserService userService;

    @Autowired
    private CryptoActiveService cryptoActiveService;

    @Autowired
    private TransactionRequestService transactionRequestService;

    @GetMapping("/getCryptoCoinsQuotes")
    @ResponseBody
    public ResponseEntity<List<CryptoCoin>> findAll(){
        return ResponseEntity.ok(operationService.findAll());
    }


    @PostMapping("/createIntentionPS")
    @ResponseBody
    public ResponseEntity<String> createIntentionPurchaseSale(@Valid @RequestBody IntentionPSDTO intention) throws IOException {
        User user = userService.getUserByEmail(intention.getUserEmail());
        CryptoActive cryptoActive = cryptoActiveService.save(new CryptoActive(intention.getCryptoCoinName(), intention.getAmountOfCryptoCoin()));
        TransactionRequest transactionRequest = operationService.createIntentionPurchaseSale(user, cryptoActive, intention.getTransactionType());
        return ResponseEntity.ok(transactionRequestService.register(transactionRequest));
    }
}
