package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model;

import lombok.Data;

@Data
public class UserListDto {

    private String name;
    private String surname;
    private int operationsPerformed;
    private int reputation;

    public UserListDto(User user){
        this.name = user.getName();
        this.surname = user.getSurname();
        this.operationsPerformed = user.getOperationsPerformed();
        this.reputation = user.getPointsObtained();
    }

}
