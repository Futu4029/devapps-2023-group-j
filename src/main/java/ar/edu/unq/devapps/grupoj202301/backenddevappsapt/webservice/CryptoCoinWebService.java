package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webservice;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.CryptoCoinDTO;
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
    CryptoCoinService cryptoCoinService;

    @GetMapping("/getTheLast24HoursOfQuotation/{cryptoCoinName}")
    @ResponseBody
    public ResponseEntity<CryptoCoin> getTheLast24HoursOfQuotation(@PathVariable("cryptoCoinName") String cryptoCoinName){
        return ResponseEntity.ok(cryptoCoinService.findCryptoCoinWithQuotationByDatesWithin24Hours(cryptoCoinName));
    }

    @GetMapping("/getCryptoCoinsQuotations")
    @ResponseBody
    public ResponseEntity<List<CryptoCoinDTO>> getCryptoCoinsQuotations(){
        return ResponseEntity.ok(cryptoCoinService.getCryptoCoinsQuotations());
    }
}
