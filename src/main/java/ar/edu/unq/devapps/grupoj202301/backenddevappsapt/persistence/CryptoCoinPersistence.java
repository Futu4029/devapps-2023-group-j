package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.CryptoCoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface CryptoCoinPersistence extends JpaRepository<CryptoCoin, String> {
}