package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.initialization;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.IntentionPurchaseSale;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.flags.IntentionType;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.flags.StatusType;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.QuotationByDate;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoCoinService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.IntentionPurchaseSaleService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories.IntentionPurchaseSaleFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories.UserFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webservice.CryptoCoinWebService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webservice.UserWebService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

@Component
public class IntentionPurchaseSaleInitializerForTesting {
    protected final Log logger = LogFactory.getLog(getClass());
    private final IntentionPurchaseSaleService intentionPurchaseSaleService;
    private final UserWebService userWebService;
    private final CryptoCoinService cryptoCoinService;

    public IntentionPurchaseSaleInitializerForTesting(IntentionPurchaseSaleService intentionPurchaseSaleService, UserWebService userWebService, CryptoCoinService cryptoCoinService){
        this.userWebService = userWebService;
        this.intentionPurchaseSaleService = intentionPurchaseSaleService;
        this.cryptoCoinService = cryptoCoinService;
    }

    protected void startInitialization() throws IOException {
        logger.warn("TEST - Initializing IntentionPurchaseSale");
        BigDecimal quotation = cryptoCoinService.getExternalQuotationByName("AUDIOUSDT");
        User anyUserOne = UserFactory.anyUser();
        User anyUserTwo = UserFactory.anyUserWithAnotherEmail();
        userWebService.registerUser(anyUserOne);
        userWebService.registerUser(anyUserTwo);
        IntentionPurchaseSale intentionPurchaseSaleActiveOne = IntentionPurchaseSaleFactory.anyIntentionPurchaseSale();
        IntentionPurchaseSale intentionPurchaseSaleActiveTwo = IntentionPurchaseSaleFactory.anyIntentionPurchaseSale();
        IntentionPurchaseSale intentionPurchaseSaleActiveThree = IntentionPurchaseSaleFactory.anyIntentionPurchaseSale();
        IntentionPurchaseSale intentionPurchaseSaleCancelled = IntentionPurchaseSaleFactory.anyIntentionPurchaseSale();
        IntentionPurchaseSale intentionPurchaseSaleFinishedOne = IntentionPurchaseSaleFactory.anyIntentionPurchaseSale();
        IntentionPurchaseSale intentionPurchaseSaleFinishedTwo = IntentionPurchaseSaleFactory.anyIntentionPurchaseSale();
        intentionPurchaseSaleActiveOne.setQuotationBase(quotation);
        intentionPurchaseSaleActiveTwo.setQuotationBase(quotation.subtract(quotation.multiply(new BigDecimal("0.07"))));
        intentionPurchaseSaleActiveThree.setQuotationBase(quotation.add(quotation.multiply(new BigDecimal("0.06"))));
        intentionPurchaseSaleActiveTwo.setIntentionType(IntentionType.PURCHASE);
        intentionPurchaseSaleService.create(intentionPurchaseSaleActiveOne);
        intentionPurchaseSaleCancelled = intentionPurchaseSaleService.create(intentionPurchaseSaleCancelled);
        intentionPurchaseSaleFinishedOne = intentionPurchaseSaleService.create(intentionPurchaseSaleFinishedOne);
        intentionPurchaseSaleFinishedTwo = intentionPurchaseSaleService.create(intentionPurchaseSaleFinishedTwo);
        intentionPurchaseSaleService.create(intentionPurchaseSaleActiveTwo);
        intentionPurchaseSaleService.create(intentionPurchaseSaleActiveThree);
        intentionPurchaseSaleCancelled.setStatusType(StatusType.CANCEL);
        intentionPurchaseSaleFinishedOne.setStatusType(StatusType.FINISHED);
        intentionPurchaseSaleFinishedTwo.setStatusType(StatusType.FINISHED);
        intentionPurchaseSaleService.updateElement(intentionPurchaseSaleCancelled);
        intentionPurchaseSaleService.updateElement(intentionPurchaseSaleFinishedOne);
        intentionPurchaseSaleService.updateElement(intentionPurchaseSaleFinishedTwo);
    }
}