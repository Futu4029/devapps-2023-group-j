package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class TransactionRequestVolumeInfo {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
    private LocalDateTime date;
    private BigDecimal dollarAmount;
    private BigDecimal pesosAmount;
    private List<CryptoActiveVolumeInfo> cryptoActiveVolumeInfoList;
}
