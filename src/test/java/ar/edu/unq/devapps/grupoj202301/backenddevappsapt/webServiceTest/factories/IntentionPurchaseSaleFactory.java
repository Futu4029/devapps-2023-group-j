package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.IntentionPurchaseSale;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.flags.IntentionType;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;

import java.math.BigDecimal;

public class IntentionPurchaseSaleFactory {

    public static IntentionPurchaseSale anyIntentionPurchaseSale(){
        User anyUser = UserFactory.anyUser();
        return new IntentionPurchaseSale(anyUser, "NEOUSDT", new BigDecimal("1"), new BigDecimal("1"), new BigDecimal("1"), IntentionType.SELL);
    }

}
