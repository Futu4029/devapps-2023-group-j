package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.anotation.NumbersOnlyAdmits;

import java.math.BigDecimal;

public class IntentionPurchaseSaleInteraction {
    private String cryptoCoinName;
    private String amountOfCryptoCoin;
    private BigDecimal quotationBase;
    private String email;
    @NumbersOnlyAdmits
    private String address;
}
