package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.TransactionData;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionType;

public class TransactionDataFactory {
    private static User user = UserFactory.anyUser();
    public static TransactionData anyTransactionDataPurchase(){
        return new TransactionData(1L,"exampleCoin", "10", "100", user, TransactionType.PURCHASE);
    }

    public static TransactionData anyTransactionDataSell(){
        return new TransactionData(2L,"exampleCoin", "10", "100", user, TransactionType.SELL);
    }
}
