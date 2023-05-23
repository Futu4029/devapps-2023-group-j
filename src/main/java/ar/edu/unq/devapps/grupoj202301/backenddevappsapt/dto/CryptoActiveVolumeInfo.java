package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
public class CryptoActiveVolumeInfo {

    private String name;
    private BigDecimal amount;
    private BigDecimal quotation;
    private BigDecimal pesosQuotation;

}
