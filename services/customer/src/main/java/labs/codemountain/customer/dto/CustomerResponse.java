package labs.codemountain.customer.dto;

import labs.codemountain.customer.Address;
import lombok.Builder;

@Builder
public record CustomerResponse(
    String id,
    String firstName,
    String lastName,
    String email,
    Address address
) {
}
