package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoCoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;

@Repository
public interface CryptoCoinPersistence extends JpaRepository<CryptoCoin, String> {
    CryptoCoin findByName(String name);

    @Query("SELECT c.quotation FROM CryptoCoin c WHERE c.name = :name")
    BigDecimal getQuotationByName(@Param("name")String name);
}
