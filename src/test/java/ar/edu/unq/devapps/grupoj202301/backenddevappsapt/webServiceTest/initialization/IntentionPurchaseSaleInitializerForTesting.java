package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.initialization;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.IntentionPurchaseSale;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.flags.IntentionType;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.flags.StatusType;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.IntentionPurchaseSaleService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.UserService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories.IntentionPurchaseSaleFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories.UserFactory;
import jakarta.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;

@Component
@Transactional
@Profile("test")
public class IntentionPurchaseSaleInitializerForTesting {

    public IntentionPurchaseSaleInitializerForTesting(IntentionPurchaseSaleService intentionPurchaseSaleService, UserService userService){
        this.userService = userService;
        this.intentionPurchaseSaleService = intentionPurchaseSaleService;
    }
    protected final Log logger = LogFactory.getLog(getClass());

    private final IntentionPurchaseSaleService intentionPurchaseSaleService;
    private final UserService userService;

    @PostConstruct
    public void initialize() throws IOException {
        logger.warn("Init Data Using DataBase Test - Initializing IntentionPurchaseSale");
        startInitialization();
    }

    private void startInitialization() {
        User anyUserOne = UserFactory.anyUser();
        User anyUserTwo = UserFactory.anyUserWithAnotherEmail();
        userService.registerElement(anyUserOne);
        userService.registerElement(anyUserTwo);
        IntentionPurchaseSale intentionPurchaseSaleActiveOne = IntentionPurchaseSaleFactory.anyIntentionPurchaseSale();
        IntentionPurchaseSale intentionPurchaseSaleActiveTwo = IntentionPurchaseSaleFactory.anyIntentionPurchaseSale();
        IntentionPurchaseSale intentionPurchaseSaleActiveThree = IntentionPurchaseSaleFactory.anyIntentionPurchaseSale();
        IntentionPurchaseSale intentionPurchaseSaleCancelled = IntentionPurchaseSaleFactory.anyIntentionPurchaseSale();
        IntentionPurchaseSale intentionPurchaseSaleFinishedOne = IntentionPurchaseSaleFactory.anyIntentionPurchaseSale();
        IntentionPurchaseSale intentionPurchaseSaleFinishedTwo = IntentionPurchaseSaleFactory.anyIntentionPurchaseSale();
        intentionPurchaseSaleActiveTwo.setQuotationBase(new BigDecimal("0.01"));
        intentionPurchaseSaleActiveThree.setQuotationBase(new BigDecimal("0.130"));
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