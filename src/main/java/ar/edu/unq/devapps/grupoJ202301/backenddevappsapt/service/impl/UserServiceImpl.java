package ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.service.impl;

import ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.persistence.UserPersistence;
import ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserPersistence userPersistence;

    @Override
    public void registerUser(){

    }
}
