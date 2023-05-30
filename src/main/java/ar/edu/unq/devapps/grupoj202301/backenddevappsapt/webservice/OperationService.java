package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webservice;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.crypto.CryptoCoinDTO;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoCoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/operation")
public class OperationService {

    @Autowired
    CryptoCoinService cryptoCoinService;

    @GetMapping("/getCryptoCoinsQuotations/{cryptoCoinName}")
    @ResponseBody
    public ResponseEntity<CryptoCoinDTO> getTheLast24HoursOfQuotation(@PathVariable("cryptoCoinName") String cryptoCoinName){
        return ResponseEntity.ok(cryptoCoinService.findCryptoCoinWithQuotationByDatesWithin24Hours(cryptoCoinName));
    }
}
