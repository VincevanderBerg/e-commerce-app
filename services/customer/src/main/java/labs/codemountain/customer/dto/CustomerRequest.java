package labs.codemountain.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import labs.codemountain.customer.Address;

public record CustomerRequest(
        @NotNull(message = "Customer first name is required.")
        String firstName,

        @NotNull(message = "Customer last name is required.")
        String lastName,

        @NotNull(message = "Customer email is required.")
        @Email(message = "Customer email is invalid.")
        String email,
        Address address
) {
}
