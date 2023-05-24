package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webservice;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoCoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@ControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/cryptocoins")
public class CryptoCoinWebService {

    @Autowired
    private CryptoCoinService  cryptoCoinService;

    @GetMapping("/getCryptoCoinsQuotations")
    @ResponseBody
    public ResponseEntity<List<CryptoCoin>> getAllQuotations(){
        return ResponseEntity.ok(cryptoCoinService.getAllQuotations());
    }
}