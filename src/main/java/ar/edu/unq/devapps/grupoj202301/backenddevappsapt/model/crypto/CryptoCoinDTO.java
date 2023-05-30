package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.crypto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Data
@RequiredArgsConstructor
public class CryptoCoinDTO {
    private String name;
    private List<QuotationByDate> quotationByDates;

    public CryptoCoinDTO(String name, List<QuotationByDate> quotationByDates) {
        this.name = name;
        this.quotationByDates = quotationByDates;
    }
}