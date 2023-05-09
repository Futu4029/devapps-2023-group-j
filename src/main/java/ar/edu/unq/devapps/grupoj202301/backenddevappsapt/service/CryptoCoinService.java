package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoCoin;

import java.util.List;

public interface CryptoCoinService {
    List<CryptoCoin> findAll();
    void saveAll(List<CryptoCoin> list);
}
