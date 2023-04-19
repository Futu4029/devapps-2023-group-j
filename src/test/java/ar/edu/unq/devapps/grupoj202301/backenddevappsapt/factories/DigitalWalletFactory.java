package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.DigitalWallet;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;

public class DigitalWalletFactory {
    public static DigitalWallet anyDigitalWallet(){
        User anyUser = UserFactory.anyUser();
        return new DigitalWallet("00000000", anyUser);
    }
}
