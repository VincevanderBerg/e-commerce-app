package labs.codemountain.customer;

import jakarta.validation.Valid;
import labs.codemountain.customer.dto.CustomerRequest;
import labs.codemountain.customer.dto.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public CustomerResponse getCustomer(@PathVariable String id) {
        return customerService.findCustomerById(id);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public Iterable<CustomerResponse> getAllCustomers() {
        return customerService.findAllCustomers();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CustomerResponse createCustomer(@RequestBody @Valid CustomerRequest request) {
        return customerService.createCustomer(request);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.OK)
    public CustomerResponse updateCustomer(@RequestBody @Valid CustomerRequest request) {
        return customerService.updateCustomer(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomerByName(id);
    }
}
