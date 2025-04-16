package labs.codemountain.customer;

import labs.codemountain.customer.dto.CustomerRequest;
import labs.codemountain.customer.dto.CustomerResponse;
import labs.codemountain.customer.exceptions.BadCustomerRequestException;
import labs.codemountain.customer.exceptions.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public final CustomerResponse findCustomerById(String id) {

        return repository.findById(id)
                .map(mapper::toCustomerResponse)
                .orElseThrow(
                        () -> new CustomerNotFoundException("Could not find customer:: Customer with id " + id + " does not exist")
                );
    }

    public final List<CustomerResponse> findAllCustomers() {
        return repository.findAll().stream()
                .map(mapper::toCustomerResponse)
                .toList();
    }

    public final CustomerResponse createCustomer(CustomerRequest request) {

        if (repository.existsByEmail(request.email())) {
            throw new BadCustomerRequestException("Could not create customer:: Customer with email " + request.email() + " already exists");
        }

        return mapper.toCustomerResponse(repository.save(mapper.toCustomer(request)));
    }

    public final CustomerResponse updateCustomer(String id, CustomerRequest request) {

        Customer customer = repository.findById(id).orElseThrow(
                () -> new CustomerNotFoundException("Could not update customer:: Customer with id " + id + " does not exist")
        );

        boolean changes = false;

        if (!customer.getFirstName().equals(request.firstName())) {
            customer.setFirstName(request.firstName());
            changes = true;
        }

        if (!customer.getLastName().equals(request.lastName())) {
            customer.setLastName(request.lastName());
            changes = true;
        }

        if (!customer.getEmail().equals(request.email())) {
            customer.setEmail(request.email());
            changes = true;
        }

        if (!changes) {
            throw new BadCustomerRequestException("Could not update customer:: No changes detected in request");
        }

        return mapper.toCustomerResponse(repository.save(customer));
    }

    public void deleteCustomerByName(String id) {
        if (!repository.existsById(id)) {
            throw new CustomerNotFoundException("Could not delete customer:: Customer with id " + id + " not found");
        }

        repository.deleteById(id);
    }
}
