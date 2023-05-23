package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.DigitalWallet;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DigitalWalletPersistence extends JpaRepository<DigitalWallet, String> {}