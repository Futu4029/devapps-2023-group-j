package ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name="users")
public class User {

    @Id
    //@SequenceGenerator(name = "walletSeq", sequenceName = "walletSeq", initialValue = 00000001, allocationSize = 1)
    //@GeneratedValue(generator = "walletSeq")
    private String walletAddress;

    @Column(nullable = false)
    @Size(min=3, max=30)
    private String name;


    @Column(nullable = false)
    @Size(min=3, max=30)
    private String surname;



    @Column(nullable = false)
    @Email
    private String email;


    @Column(nullable = false)
    @Size(min=10, max=30)
    private String address;

    @Column(nullable = false)
    @Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*().,<>{}[\\]<>?_=+\\-|;:\\'\\\"\\/]])(?!.*\\s).{8,20}$")
    @Size(min=6, max=32)
    private String password;


    @Column(nullable = false)
    @Size(min=22, max=22)
    private String cvu;





}
