package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.initializer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransactionRequestInitializer {

    /*protected final Log logger = LogFactory.getLog(getClass());

    @Value("${spring.datasource.driver-class-name:NONE}")
    private String className;

    @Autowired
    private TransactionRequestService transactionRequestService;
    @Autowired
    private CryptoCoinService cryptoCoinService;
    @Autowired
    private UserService userService;

    @PostConstruct
    public void initialize(){
        String h2_CLASS_NAME = "org.h2.Driver";

        if (className.equals(h2_CLASS_NAME)){
            logger.warn("Init Data Using H2 DataBase - Initializing CryptoCoins");
            startInitialization();
        }else{
            logger.warn("No database was set for the initialization");
        }
    }

    private void startInitialization() {
        List<CryptoCoin> cryptoCoinList = cryptoCoinService.findAll();
        User user = new User("example@example.com", "00000000", "example", "example", "exampleAddres", "exampleP1!", "0123456789012345678900");
        List<TransactionRequest> transactionRequestList = List.of(
               /*
                new TransactionRequest(cryptoCoinList.get(0), new BigDecimal("100"),LocalDateTime.now(), user, new BigDecimal("1"), new BigDecimal("0.001"), TransactionType.SELL),
                new TransactionRequest(cryptoCoinList.get(1), new BigDecimal("100"),LocalDateTime.now(), user, new BigDecimal("1"), new BigDecimal("0.001"), TransactionType.SELL),
                new TransactionRequest(cryptoCoinList.get(2), new BigDecimal("100"),LocalDateTime.now(), user, new BigDecimal("1"), new BigDecimal("0.001"), TransactionType.SELL),
                new TransactionRequest(cryptoCoinList.get(3), new BigDecimal("100"),LocalDateTime.now(), user, new BigDecimal("1"), new BigDecimal("0.001"), TransactionType.SELL),
                new TransactionRequest(cryptoCoinList.get(4), new BigDecimal("100"),LocalDateTime.now(), user, new BigDecimal("1"), new BigDecimal("0.001"), TransactionType.SELL),
                new TransactionRequest(cryptoCoinList.get(5), new BigDecimal("100"),LocalDateTime.now(), user, new BigDecimal("1"), new BigDecimal("0.001"), TransactionType.SELL),
                new TransactionRequest(cryptoCoinList.get(6), new BigDecimal("100"),LocalDateTime.now(), user, new BigDecimal("1"), new BigDecimal("0.001"), TransactionType.SELL),
                new TransactionRequest(cryptoCoinList.get(7), new BigDecimal("100"),LocalDateTime.now(), user, new BigDecimal("1"), new BigDecimal("0.001"), TransactionType.SELL),
                new TransactionRequest(cryptoCoinList.get(8), new BigDecimal("100"),LocalDateTime.now(), user, new BigDecimal("1"), new BigDecimal("0.001"), TransactionType.SELL),
                new TransactionRequest(cryptoCoinList.get(9), new BigDecimal("100"),LocalDateTime.now(), user, new BigDecimal("1"), new BigDecimal("0.001"), TransactionType.SELL),
                new TransactionRequest(cryptoCoinList.get(10), new BigDecimal("100"),LocalDateTime.now(), user, new BigDecimal("1"), new BigDecimal("0.001"), TransactionType.SELL),
                new TransactionRequest(cryptoCoinList.get(11), new BigDecimal("100"),LocalDateTime.now(), user, new BigDecimal("1"), new BigDecimal("0.001"), TransactionType.SELL),
                new TransactionRequest(cryptoCoinList.get(12), new BigDecimal("100"),LocalDateTime.now(), user, new BigDecimal("1"), new BigDecimal("0.001"), TransactionType.SELL),
                new TransactionRequest(cryptoCoinList.get(13), new BigDecimal("100"),LocalDateTime.now(), user, new BigDecimal("1"), new BigDecimal("0.001"), TransactionType.SELL)
        );
        transactionRequestService.saveAll(transactionRequestList);
    }

*/
}
