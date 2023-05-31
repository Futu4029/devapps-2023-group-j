package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webservice;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.CryptoCoinDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/cryptocoins")
public class CryptoCoinWebService {

    @Autowired
    ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoCoinService cryptoCoinService;

    @GetMapping("/getTheLast24HoursOfQuotation/{cryptoCoinName}")
    @ResponseBody
    public ResponseEntity<CryptoCoinDTO> getTheLast24HoursOfQuotation(@PathVariable("cryptoCoinName") String cryptoCoinName){
        return ResponseEntity.ok(cryptoCoinService.findCryptoCoinWithQuotationByDatesWithin24Hours(cryptoCoinName));
    }
}
