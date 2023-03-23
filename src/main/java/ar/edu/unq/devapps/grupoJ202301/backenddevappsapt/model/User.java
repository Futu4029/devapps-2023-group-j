package ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
public class User {

    @Id
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String address;
    private String password;
    private String cvu;
    private String walletAddress;


    public void Register() {
        //TODO: Pegarle a la base
    }
}
