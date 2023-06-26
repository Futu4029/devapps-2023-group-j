package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.anotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@RequiredArgsConstructor
@AllArgsConstructor
public class User implements GenericSystemElement {

    @Id
    @Column(nullable = false, unique=true)
    @Email(message = "Please provide a valid email address")
    @NotBlank
    private String email;

    @Column(nullable = false, unique = true)
    @Size(min=8, max=8)
    @LettersAndNumbersOnlyAdmits
    @NotBlank
    private String walletAddress;

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
    @SpecialCharactersOnlyAdmits
    @PasswordSize
    private String password;

    @Column(nullable = false)
    @Size(min=22, max=22)
    @NumbersOnlyAdmits
    private String cvu;

    @Column(nullable = false)
    @NotNull
    private int pointsObtained = 0;

    @Column(nullable = false)
    @NotNull
    private int operationsPerformed = 0;

    @ManyToMany
    @JoinColumn(name = "id_role")
    private List<Role> roles = new ArrayList<>();

    public User(String email, String walletAddress, String name, String surname, String address, String password, String cvu) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.password = password;
        this.cvu = cvu;
        this.walletAddress = walletAddress;
    }

    @Override
    public String getId() {
        return this.email;
    }

    public void discountPoints(int points) {
        pointsObtained = pointsObtained - Math.abs(points);
    }
    public void addPoints(int points) {
        pointsObtained = pointsObtained + Math.abs(points);
    }
}