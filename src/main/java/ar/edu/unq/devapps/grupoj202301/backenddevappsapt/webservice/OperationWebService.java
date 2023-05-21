package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webservice;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.IntentionPSDTO;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.OperationService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/getCryptoCoinsQuotes")
    @ResponseBody
    public ResponseEntity<List<CryptoCoin>> findAll(){
        return ResponseEntity.ok(operationService.findAll());
    }

    @PostMapping("/createIntentionPS")
    @ResponseBody
    public ResponseEntity<String> createIntentionPurchaseSale(@Valid @RequestBody IntentionPSDTO intentionPSDTO) {
        User user = userService.getUserByEmail(intentionPSDTO.getUserEmail());
        return ResponseEntity.ok(operationService.createIntentionPurchaseSale(user, intentionPSDTO));
    }
}
