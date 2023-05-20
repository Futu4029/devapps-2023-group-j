package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.impl;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.CryptoCoinPersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoCoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CryptoCoinServiceImpl implements CryptoCoinService {

    @Autowired
    private CryptoCoinPersistence cryptoCoinPersistence;

    @Override
    public List<CryptoCoin> findAll() {
        return cryptoCoinPersistence.findAll();
    }

    @Override
    public void saveAll(List<CryptoCoin> list) {
        cryptoCoinPersistence.saveAll(list);
    }

    @Override
    public CryptoCoin findByName(String name) {
        return cryptoCoinPersistence.findByName(name);
    }

    @Override
    public BigDecimal toPesos(String name, BigDecimal amount) {
        CryptoCoin cryptoCoin = findByName(name);
        CryptoCoin pesosCoin = findByName("PESOS");
        BigDecimal dollars = cryptoCoin.getPrice().multiply(amount);
        return dollars.multiply(pesosCoin.getPrice());
    }

}
