package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webservice;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.TransactionRequestVolumeInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.TransactionRequest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.TransactionRequestService;
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
@RequestMapping("/transactionrequest")
public class TransactionRequestWebService {

    @Autowired
    private TransactionRequestService transactionRequestService;

    @GetMapping
    public ResponseEntity<List<TransactionRequest>> findAll(){
        return ResponseEntity.ok(transactionRequestService.findAll());
    }

    @GetMapping("/betweenDates/{email}/{startDate}/{endDate}")
    private ResponseEntity<TransactionRequestVolumeInfo> volumeOperatedBetweenDates(@PathVariable("email") String email, @PathVariable("startDate") LocalDateTime startDate,@PathVariable("endDate") LocalDateTime endDate) throws IOException {
        return ResponseEntity.ok(transactionRequestService.volumeOperatedBetweenDates(email, startDate, endDate));
    }
}
