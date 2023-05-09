package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.impl;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.CryptoCoinPersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoCoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
