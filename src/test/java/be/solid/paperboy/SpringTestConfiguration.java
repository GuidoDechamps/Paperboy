package be.solid.paperboy;

import be.solid.paperboy.model.*;
import be.solid.paperboy.service.DeliveryStrategy;
import be.solid.paperboy.service.FactoriesForTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringTestConfiguration {


    @Bean
    public InMemCustomerRepository customerRepository() {
        return new InMemCustomerRepository();
    }

    @Bean
    public CustomerDTOMapper customerDTOMapper() {
        return new CustomerDTOMapper(customerFactory());
    }

    @Bean
    public CustomersForStreetFactory CustomerForStreetFactory() {
        return new CustomersForStreetFactory(customerFactory());
    }

    @Bean
    public InMemPaperBoyRepository inMemPaperBoyRepository() {
        return new InMemPaperBoyRepository();
    }

    @Bean
    public FixedPaperPriceService fixedPaperPriceService() {
        return FixedPaperPriceService.createDefault();
    }

    @Bean
    public PaperDeliveryApplicationService paperDeliveryApplicationService() {
        return new ApplicationService(deliveryStrategy(), inMemPaperBoyRepository());
    }


    @Bean
    public PaperFactory paperFactory() {
        return new PaperFactory(fixedPaperPriceService());
    }

    @Bean
    public WalletFactory walletFactory() {
        return new WalletFactory(FactoriesForTest.DEFAULT_CURRENCY_CODE);
    }

    @Bean
    public PaperBoyFactory paperBoyFactory() {
        return new PaperBoyFactory(walletFactory());
    }

    @Bean
    public PaperBoySetFactory paperBoysFactory() {
        return new PaperBoySetFactory(paperBoyFactory());
    }

    @Bean
    public CustomerFactory customerFactory() {
        return new CustomerFactory(walletFactory());
    }


    @Bean
    public DeliveryStrategy deliveryStrategy() {
        return FactoriesForTest.createDeliveryStrategy(paperFactory(), customerRepository());
    }

    @Bean
    public MoneyInCirculationService moneyInCirculationValidator() {
        return new MoneyInCirculationService(inMemPaperBoyRepository(), customerRepository());
    }
}
