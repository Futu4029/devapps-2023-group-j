package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.TransactionRequest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;

public interface UserService {
    String register(User user);
    User getUserByEmail(String email);
    String confirmReception(User user, TransactionRequest transactionRequest);
    String discountReputation(User user, TransactionRequest transactionRequest);
}
