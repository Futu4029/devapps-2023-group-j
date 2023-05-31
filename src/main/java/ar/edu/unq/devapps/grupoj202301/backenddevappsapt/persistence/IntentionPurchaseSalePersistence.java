package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.IntentionPurchaseSale;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.StatusType;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.CryptoCoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IntentionPurchaseSalePersistence extends JpaRepository<IntentionPurchaseSale, String> {

    @Query("SELECT ips FROM IntentionPurchaseSale ips WHERE ips.email = :email AND ips.statusType = :statusType")
    List<IntentionPurchaseSale> getActivesTransactions(@Param("email") String email, @Param("statusType") StatusType statusType);

    @Query("SELECT ips FROM IntentionPurchaseSale ips WHERE ips.email = :email AND ips.statusType = :statusType AND ips.creationDate BETWEEN :startDate AND :endDate")
    List<IntentionPurchaseSale> findOperationBetweenDates(String email, LocalDateTime startDate, LocalDateTime endDate, StatusType statusType);
}
