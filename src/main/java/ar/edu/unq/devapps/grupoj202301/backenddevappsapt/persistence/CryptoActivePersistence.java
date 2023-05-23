package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoActive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoActivePersistence extends JpaRepository<CryptoActive, Long> {
}
