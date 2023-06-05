package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
@Data
@RequiredArgsConstructor
public class VolumeInfoInitialData {
    @NotBlank
    @Email
    private String email;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    public VolumeInfoInitialData(String email, LocalDateTime startDate, LocalDateTime endDate) {
        this.email = email;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
