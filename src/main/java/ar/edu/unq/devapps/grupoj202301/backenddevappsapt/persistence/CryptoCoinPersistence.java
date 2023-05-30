package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.crypto.CryptoCoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface CryptoCoinPersistence extends JpaRepository<CryptoCoin, String> {
    @Query("SELECT c FROM CryptoCoin c JOIN FETCH c.quotationByDates q " +
            "WHERE c.name = :cryptoCoinName AND q.date >= :startDate")
    CryptoCoin findCryptoCoinWithQuotationByDatesWithin24Hours(@Param("cryptoCoinName") String cryptoCoinName,
                                                               @Param("startDate") LocalDateTime startDate);
}