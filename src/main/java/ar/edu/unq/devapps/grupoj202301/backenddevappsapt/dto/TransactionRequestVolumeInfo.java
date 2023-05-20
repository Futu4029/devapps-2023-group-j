package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class TransactionRequestVolumeInfo {

    private LocalDateTime date;
    private BigDecimal dollarAmount;
    private BigDecimal pesosAmount;
    private List<CryptoActiveVolumeInfo> cryptoActiveVolumeInfoList;
}
