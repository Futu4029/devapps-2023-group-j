package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoCoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoCoinPersistence extends JpaRepository<CryptoCoin, String> {
    CryptoCoin findByName(String name);
}
