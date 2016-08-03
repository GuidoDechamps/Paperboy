package be.solid.paperboy;

import be.solid.paperboy.model.*;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.money.MonetaryAmount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringTestConfiguration.class})
@DirtiesContext
public class ApplicationServiceTest {

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private InMemCustomerRepository customerRepository;
    @Autowired
    private InMemPaperBoyRepository paperBoyRepository;
    @Autowired
    private MoneyInCirculationService moneyInCirculationService;
    @Autowired
    private PaperBoySetFactory paperBoySetFactory;
    @Autowired
    private CustomersForStreetFactory customersForStreetFactory;
    private MonetaryAmount initialMoneyInCirculation;


    @Before
    public void setUp() throws Exception {
        customerRepository.setCustomersOnStreetDistribution(buildCustomers());
        paperBoyRepository.setPaperBoys(paperBoySetFactory.buildPaperBoys());
        initialMoneyInCirculation = moneyInCirculationService.countAllTheMoneyInCirculation();
    }

    @Test
    public void deliverPapers() throws Exception {
        applicationService.deliverPapers(Streets.ALL_STREETS);

        verify();
    }

    public void checkMoneyInCirculation() {
        assertEquals("The amount of money in ciculation was incoorect. Did someone create money?",
                initialMoneyInCirculation,
                moneyInCirculationService.countAllTheMoneyInCirculation());
    }

    private ImmutableSet<Customer> buildCustomers() {
        return customersForStreetFactory.buildCustomersPerStreet(Streets.ALL_STREETS);
    }

    private void verify() {
        checkPaperDelivered();
        checkMoneyInCirculation();
    }

    private void checkPaperDelivered() {
        customerRepository.getAll().forEach(this::checkHasPaper);
    }

    private void checkHasPaper(Customer x) {
        if (isCustomerWhoDidntHaveAnyMoneyToStartWith(x))
            assertTrue(String.format("The customer {%s} without money obtained a paper", x), x.getPaper() == null);
        else
            assertTrue(String.format("The customer {%s} with money has no paper", x), x.getPaper() != null);
    }

    private boolean isCustomerWhoDidntHaveAnyMoneyToStartWith(Customer x) {
        return customersForStreetFactory.isCustomerWhoDidntHaveAnyMoneyToStartWith(x);
    }


}