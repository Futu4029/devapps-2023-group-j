package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.anotation.LettersAndNumbersOnlyAdmits;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.anotation.LettersOnlyAdmits;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.anotation.NumbersOnlyAdmits;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.anotation.SpecialCharactersOnlyAdmits;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name="users")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    @Column(nullable = false, unique=true)
    @Email(message = "Please provide a valid email address")
    @NotBlank
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "digital_wallet_address", referencedColumnName = "address")
    @NotNull
    @Valid
    private DigitalWallet digitalWallet;

    @Column(nullable = false)
    @Size(min=3, max=30)
    @LettersOnlyAdmits
    private String name;

    @Column(nullable = false)
    @Size(min=3, max=30)
    @LettersOnlyAdmits
    private String surname;

    @Column(nullable = false)
    @Size(min=10, max=30)
    @LettersAndNumbersOnlyAdmits
    private String address;

    @Column(nullable = false)
    @Size(min=6, max=32)
    @SpecialCharactersOnlyAdmits
    private String password;

    @Column(nullable = false)
    @Size(min=22, max=22)
    @NumbersOnlyAdmits
    private String cvu;

    @OneToMany(mappedBy = "id")
    private List<TransactionRequest> transactionRequests = new ArrayList<>();

    @ConstructorProperties({"email", "walletAddress", "name", "address", "password", "cvu"})
    public User(String email, String walletAddress, String name, String surname, String address, String password, String cvu) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.password = password;
        this.cvu = cvu;
        this.digitalWallet = new DigitalWallet(walletAddress, email);
    }

}