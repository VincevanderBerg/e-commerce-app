package labs.codemountain.customer.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@EqualsAndHashCode(callSuper = true)
@Data
@ResponseStatus(value = NOT_FOUND)
public class CustomerNotFoundException extends RuntimeException {

    private final String message;
}
