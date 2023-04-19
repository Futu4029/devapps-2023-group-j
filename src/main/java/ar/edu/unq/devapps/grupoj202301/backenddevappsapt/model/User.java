package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.anotation.LettersAndNumbersOnlyAdmits;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.anotation.LettersOnlyAdmits;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.anotation.NumbersOnlyAdmits;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.anotation.SpecialCharactersOnlyAdmits;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

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
    @Email
    private String email;

    @PrimaryKeyJoinColumn
    @OneToOne(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DigitalWallet wallet;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    private List<TransactionRequest> transactionRequests = new ArrayList<TransactionRequest>();

    public User(String email, String walletAddress, String name, String surname, String address, String password, String cvu) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.password = password;
        this.cvu = cvu;
        this.wallet = new DigitalWallet(walletAddress, this);
    }

}
