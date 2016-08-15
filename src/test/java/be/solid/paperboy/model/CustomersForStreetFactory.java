package be.solid.paperboy.model;

import com.google.common.collect.ImmutableSet;

import java.util.Set;


public class CustomersForStreetFactory {
    private static final int NO_MONEY = 0;
    private static final int DEFAULT_CUSTOMER_MONEY_AMOUNT = 1;
    private static final String NO_MONEY_HOUSENR = buildHouseNr(NO_MONEY);

    private final CustomerFactory customerFactory;

    public CustomersForStreetFactory(CustomerFactory customerFactory) {
        this.customerFactory = customerFactory;
    }

    public boolean isCustomerWhoDidntHaveAnyMoneyToStartWith(Customer customer) {
        final String houseNr = customer.getAddress().getHouseNr();
        return NO_MONEY_HOUSENR.equals(houseNr);
    }

    public ImmutableSet<Customer> buildCustomersPerStreet(Set<String> streets) {
        final ImmutableSet.Builder<Customer> builder = ImmutableSet.builder();
        streets.stream().forEach(x -> addCustomers(builder, x));
        return builder.build();
    }

    private static String buildHouseNr(int customerMoneyAmount) {
        return "[" + customerMoneyAmount + "]";
    }

    private ImmutableSet.Builder<Customer> addCustomers(ImmutableSet.Builder<Customer> builder, String streetName) {
        return builder.add(createDefaultCustomer(streetName))
                .add(createDefaultCustomer(streetName))
                .add(createNoMoneyCustomer(streetName))
                .add(createDefaultCustomer(streetName))
                .add(createNoMoneyCustomer(streetName))
                .add(createDefaultCustomer(streetName));
    }

    private Customer createDefaultCustomer(String street) {
        return createCustomer(DEFAULT_CUSTOMER_MONEY_AMOUNT, street);
    }

    private Customer createNoMoneyCustomer(String street) {
        return createCustomer(NO_MONEY, street);
    }

    private Customer createCustomer(int customerMoneyAmount, String street) {
        return customerFactory.createCustomer(street, customerMoneyAmount, buildHouseNr(customerMoneyAmount));

    }


}
