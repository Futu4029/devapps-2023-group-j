package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.initialization;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
@Profile("test")
public class CoreInitializerForTesting {

    @Autowired
    private RoleInitializerForTesting roleInitializerForTesting;

    @Autowired
    private CryptoCoinInitializerForTesting cryptoCoinInitializerForTesting;

    @Autowired
    private IntentionPurchaseSaleInitializerForTesting intentionPurchaseSaleInitializerForTesting;

    @PostConstruct
    public void init() throws IOException {
        roleInitializerForTesting.startInitialization();
        cryptoCoinInitializerForTesting.startInitialization();
        intentionPurchaseSaleInitializerForTesting.startInitialization();
    }
}
