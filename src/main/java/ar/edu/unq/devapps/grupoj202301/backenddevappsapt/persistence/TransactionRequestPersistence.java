package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.TransactionRequest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionState;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRequestPersistence extends JpaRepository<TransactionRequest, Long> {

    @Query("SELECT t FROM TransactionRequest t WHERE t.date BETWEEN :startDate AND :endDate " +
            "AND t.transactionState= :state " +
            "AND t.userOwner.email = :email")
    List<TransactionRequest>findOperationBetweenDates(@Param("email")String email,
                                                      @Param("startDate")LocalDateTime startDate,
                                                      @Param("endDate")LocalDateTime endDate,
                                                      @Param("state")TransactionState state);

    @Query("SELECT t FROM TransactionRequest t WHERE t.transactionState= :state")
    List<TransactionRequest>getTransactionsByState(@Param("state")TransactionState state);
}
