package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.anotation.LettersAndNumbersOnlyAdmits;
import jakarta.persistence.*;
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
    private String address;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    private User owner;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    private List<CryptoActive> cryptoCoinsAcquired = new ArrayList<>();

    public DigitalWallet(String address, User owner) {
        this.address = address;
        this.owner = owner;
    }
}
