package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.impl;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.DigitalWallet;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.CryptoCoinPersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.DigitalWalletPersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.DigitalWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DigitalWalletServiceImpl implements DigitalWalletService {

    @Autowired
    private DigitalWalletPersistence digitalWalletPersistence;

    @Override
    public DigitalWallet getDigitalWallet(String address) {
        return digitalWalletPersistence.findById(address).orElseThrow();
    }
}
