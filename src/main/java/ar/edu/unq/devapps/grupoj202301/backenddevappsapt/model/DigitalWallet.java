package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.anotation.LettersAndNumbersOnlyAdmits;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="digital_wallet")
@NoArgsConstructor
public class DigitalWallet {

    @Id
    @Column(nullable = false, unique = true)
    @Size(min=8, max=8)
    @LettersAndNumbersOnlyAdmits
    @NotBlank
    private String address;

    @OneToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    @NotNull
    private User owner;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    @NotNull
    private List<CryptoActive> cryptoCoinsAcquired = new ArrayList<>();

    public DigitalWallet(String address, User owner) {
        this.address = address;
        this.owner = owner;
    }
}
