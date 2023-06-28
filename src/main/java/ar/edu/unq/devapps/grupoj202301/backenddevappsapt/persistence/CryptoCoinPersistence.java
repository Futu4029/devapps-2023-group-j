package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.CryptoCoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CryptoCoinPersistence extends JpaRepository<CryptoCoin, String> {
    @Query("SELECT c FROM CryptoCoin c JOIN FETCH c.quotationByDates q " +
            "WHERE c.name = :cryptoCoinName AND q.date BETWEEN :startDate AND :endDate")
    CryptoCoin findCryptoCoinWithQuotationByDatesWithin24Hours(
            @Param("cryptoCoinName") String cryptoCoinName,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT c FROM CryptoCoin c JOIN FETCH c.quotationByDates q " +
            "WHERE q.date = (SELECT MAX(qd.date) FROM CryptoCoin cc JOIN cc.quotationByDates qd WHERE cc = c)")
    List<CryptoCoin> getCryptoCoinsQuotations();

    @Query("SELECT c.name FROM CryptoCoin c")
    List<String> getAllCryptoCoinNames();
}