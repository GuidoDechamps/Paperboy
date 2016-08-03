package be.solid.paperboy.model;

import be.solid.paperboy.common.GuavaCollectors;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

public class InMemCustomerRepository implements CustomerRepository {

    private ImmutableSetMultimap<String, Customer> customers = null;


    public void setCustomersOnStreetDistribution(Collection<Customer> customers) {
        checkNotNull(customers);
        if (this.customers == null)
            this.customers = toMap(customers);
        else
            throwAlreadyInitializedError();
    }

    @Override
    public Set<Customer> getAll(String street) {
        final ImmutableSetMultimap<String, Customer> customers = getCustomers();
        if (customers.containsKey(street))
            return customers.get(street);
        else
            return ImmutableSet.of();
    }

    @Override
    public Set<Customer> getAll() {
        return ImmutableSet.copyOf(getCustomers().values());
    }

    public Customer getCustomerForAddress(String street, String houseNr) {
        final ImmutableSet<Customer> customersOnStreet = getCustomers().get(street);
        final List<Customer> customers = customersOnStreet.stream().filter(x -> x.getAddress().getHouseNr().equals(houseNr)).collect(Collectors.toList());
        validateSingleCustomer(customers, street, houseNr);
        return customers.get(0);
    }


    private void validateSingleCustomer(List<Customer> customers, String street, String houseNr) {
        if (customers.isEmpty())
            throw new RuntimeException("No customer lives on " + street + " " + houseNr);
        if (customers.size() > 1)
            throw new RuntimeException("More then one customer lives on " + street + " " + houseNr + ". Customers; " + customers);
    }

    private ImmutableSetMultimap<String, Customer> getCustomers() {
        if (customers == null)
            throw new RuntimeException("InMemCustomerRepository was not initialized");
        else
            return customers;
    }


    private ImmutableSetMultimap<String, Customer> toMap(Collection<Customer> customers) {
        return customers.stream()
                .collect(GuavaCollectors.toImmutableSetMap(x -> x.getAddress().getStreet(),
                        Function.identity()));
    }

    private void throwAlreadyInitializedError() {
        throw new RuntimeException("InMemCustomerRepository were already initialised");
    }

}
