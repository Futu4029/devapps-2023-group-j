package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webservice;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.IntentionPurchaseSale;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.IntentionPurchaseSaleCoreData;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.IntentionPurchaseSaleUserInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.IntentionPurchaseSaleVolumeInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.IntentionPurchaseSaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@ControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/intention")
public class IntentionPurchaseSaleWebService {

    @Autowired
    private IntentionPurchaseSaleService intentionPurchaseSaleService;

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<IntentionPurchaseSale> createIntentionPurchaseSale(@Valid @RequestBody IntentionPurchaseSaleCoreData IntentionPurchaseSaleInitialData) {
        return ResponseEntity.ok(intentionPurchaseSaleService.createIntentionPurchaseSale(IntentionPurchaseSaleInitialData));
    }

    @GetMapping("/getActivesIntentions/{email}")
    @ResponseBody
    private ResponseEntity<IntentionPurchaseSaleUserInfo> getActivesTransactions(@PathVariable("email") String email) throws IOException {
        return ResponseEntity.ok(intentionPurchaseSaleService.getActivesTransactions(email));
    }

    @GetMapping("/betweenDates/{email}/{startDate}/{endDate}")
    @ResponseBody
    private ResponseEntity<IntentionPurchaseSaleVolumeInfo> volumeOperatedBetweenDates(@PathVariable("email") String email, @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS") LocalDateTime startDate, @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS") LocalDateTime endDate) throws IOException {
        return ResponseEntity.ok(intentionPurchaseSaleService.volumeOperatedBetweenDates(email, startDate, endDate));
    }
}
