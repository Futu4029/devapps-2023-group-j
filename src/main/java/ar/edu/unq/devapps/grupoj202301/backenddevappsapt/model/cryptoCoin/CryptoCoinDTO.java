package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class CryptoCoinDTO {
    private String name;

    private QuotationByDate quotationByDate;

    public CryptoCoinDTO(String name, QuotationByDate quotationByDates) {
        this.name = name;
        this.quotationByDate = quotationByDates;
    }
}