package be.solid.paperboy;

import be.solid.paperboy.model.*;
import be.solid.paperboy.service.FactoriesForTest;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.javamoney.moneta.function.MonetaryFunctions;
import org.javamoney.moneta.function.MonetarySummaryStatistics;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static be.solid.paperboy.common.GuavaCollectors.immutableSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@ContextConfiguration(classes = {SpringTestConfiguration.class})
@DirtiesContext
public class PaperDeliverySteps {
    @Autowired
    private InMemCustomerRepository inMemCustomerRepository;
    @Autowired
    private InMemPaperBoyRepository inMemPaperBoyRepository;
    @Autowired
    private FixedPaperPriceService paperPriceService;
    @Autowired
    private PaperDeliveryApplicationService applicationService;
    @Autowired
    private CustomerFactory customerFactory;
    @Autowired
    private PaperBoyFactory paperBoyFactory;
    @Autowired
    private CustomerDTOMapper customerDTOMapper;
    //////////State/////////
    private Set<String> allStreets;


    @Given("^a single customer with (\\d+) eur$")
    public void a_single_customer_with_eur(int moneyAmount) throws Throwable {
        final Customer customer = customerFactory.createCustomer(Streets.ROUTE666, moneyAmount, "" + moneyAmount);
        allStreets = Sets.newHashSet(Streets.ROUTE666);
        inMemCustomerRepository.setCustomersOnStreetDistribution(ImmutableSet.of(customer));
    }

    @Given("^a single paperboy$")
    public void a_single_paperboy() throws Throwable {
        there_are_paperboys(1);
    }


    @Then("^the single customer has (\\d+) eur left and owns a paper state is (true|false)")
    public void the_single_customer_has_eur_left_and_paper(int customerMoney, boolean expectedToHavePaper) throws Throwable {
        final Customer customer = inMemCustomerRepository.getAll().iterator().next();
        assertTrue(customer.hasMoney(FactoriesForTest.createMoney(customerMoney)));
        assertEquals(expectedToHavePaper, customer.hasPaper());
    }


    @Given("^The initial customer money distribution:$")
    public void the_initial_customer_money_distribution(List<CustomerDTO> customers) throws Throwable {
        this.allStreets = customers.stream().map(CustomerDTO::getStreet).collect(immutableSet());
        final Set<Customer> customerSet = customerDTOMapper.mapToCustomers(customers);
        this.inMemCustomerRepository.setCustomersOnStreetDistribution(customerSet);
    }

    @Given("^there are (\\d+) paperboys$")
    public void there_are_paperboys(int nrOfPaperBoys) throws Throwable {
        final ImmutableSet<PaperBoy> paperBoys = paperBoyFactory.createPaperBoys(nrOfPaperBoys);
        inMemPaperBoyRepository.setPaperBoys(paperBoys);
    }

    @Given("^the price of a paper is (\\d+) eur$")
    public void the_price_of_a_paper_is(int arg1) throws Throwable {
        paperPriceService.setPrice(arg1);
    }

    @When("^(?:the paper is delivered|the papers are delivered)$")
    public void the_papers_are_delivered() throws Throwable {
        applicationService.deliverPapers(allStreets);
    }

    @Then("^the (?:paperboys|paperboy) sold (\\d+) newspapers for a total of (\\d+) eur$")
    public void the_paperboys_sold_newspapers_for_a_total_of_eur(int expectedNrOfPapersSold, int expectedRevenue) throws Throwable {
        validateNrOfPapersSold(expectedNrOfPapersSold);
        validateExpectedRevenue(expectedRevenue);
    }


    @Then("^The resulting customer state:$")
    public void the_resulting_customer_state(List<CustomerDTO> expectedCustomerState) throws Throwable {
        expectedCustomerState.stream().forEach(this::validateCustomerMoney);
    }


    private void validateExpectedRevenue(int expectedRevenue) {
        final MonetaryAmount revenue = getRevenue();
        assertEquals(expectedRevenue, revenue.getNumber().intValue());
    }

    private void validateCustomerMoney(CustomerDTO expectedCustomerState) {
        final Customer customerForAddress = inMemCustomerRepository.getCustomerForAddress(expectedCustomerState.getStreet(), expectedCustomerState.getHouseNr());
        assertEquals(expectedCustomerState.toString(), expectedCustomerState.getMoney(), customerForAddress.getAmountOfMoney().getNumber().intValue());
        assertEquals(expectedCustomerState.toString(), expectedCustomerState.getOwnsPaper(), customerForAddress.hasPaper());
    }


    private void validateNrOfPapersSold(int expectedNrOfpapersSold) {
        final long nrOfUnsoldPapers = getNrOfPapersUnsold();
        final int nrOfPapersPrinted = getNrOfPapersPrinted();
        assertEquals(expectedNrOfpapersSold, nrOfPapersPrinted - nrOfUnsoldPapers);
    }

    private int getNrOfPapersPrinted() {
        return inMemCustomerRepository.getAll().size();
    }

    private long getNrOfPapersUnsold() {
        final Set<PaperBoy> paperBoys = inMemPaperBoyRepository.getAll();
        return paperBoys.stream()
                .mapToInt(PaperBoy::getNrOfPapers)
                .sum();
    }

    private MonetaryAmount getRevenue() {
        final Set<PaperBoy> paperBoys = inMemPaperBoyRepository.getAll();
        final MonetarySummaryStatistics monetaryAmounts = getMonetarySummaryStatistics(paperBoys);
        return monetaryAmounts.getSum();
    }

    private MonetarySummaryStatistics getMonetarySummaryStatistics(Set<PaperBoy> paperBoys) {
        final Map<CurrencyUnit, MonetarySummaryStatistics> collect = paperBoys.stream()
                .map(PaperBoy::getAmountOfMoney)
                .collect(MonetaryFunctions.groupBySummarizingMonetary()).get();
        return getSingleStatistic(collect);
    }

    private MonetarySummaryStatistics getSingleStatistic(Map<CurrencyUnit, MonetarySummaryStatistics> collect) {
        if (collect.keySet().isEmpty())
            Assert.fail("There should exactly one currency used but no money was found.");
        if (collect.keySet().size() > 1)
            Assert.fail("There should only be one currency used but the follwoing currecnies were found: " + collect.keySet());
        return collect.entrySet().iterator().next().getValue();
    }


}
