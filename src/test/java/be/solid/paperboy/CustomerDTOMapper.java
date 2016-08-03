package be.solid.paperboy;

import be.solid.paperboy.common.GuavaCollectors;
import be.solid.paperboy.model.Customer;
import be.solid.paperboy.model.CustomerFactory;

import java.util.Collection;
import java.util.Set;

class CustomerDTOMapper {
    private final CustomerFactory customerFactory;

    public CustomerDTOMapper(CustomerFactory customerFactory) {
        this.customerFactory = customerFactory;
    }

    public Customer map(CustomerDTO dto) {
        return customerFactory.createCustomer(dto.getStreet(), dto.getMoney(), dto.getHouseNr());
    }

    public Set<Customer> mapToCustomers(Collection<CustomerDTO> customerDTOs) {
        return customerDTOs.stream()
                .map(this::map)
                .collect(GuavaCollectors.immutableSet());
    }
}
