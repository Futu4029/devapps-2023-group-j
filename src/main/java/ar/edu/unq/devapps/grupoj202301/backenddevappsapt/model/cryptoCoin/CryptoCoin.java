package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.GenericSystemElement;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="crypto_coin")
@AllArgsConstructor
@RequiredArgsConstructor
public class CryptoCoin implements GenericSystemElement  {

    @Id
    @Column(nullable = false, unique = true)
    @NotBlank
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "crypto_coin_id")
    private List<QuotationByDate> quotationByDates = new ArrayList<>();

    public CryptoCoin(String name) {
        this.name = name;
    }

    public void addQuotation(QuotationByDate quotationByDate) {
        this.quotationByDates.add(quotationByDate);
    }

    @Override
    public String getId() {
        return this.name;
    }
}