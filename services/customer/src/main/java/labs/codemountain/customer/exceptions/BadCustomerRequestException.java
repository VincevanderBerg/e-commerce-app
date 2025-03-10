package labs.codemountain.customer.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@EqualsAndHashCode(callSuper = true)
@Data
@ResponseStatus(value = BAD_REQUEST)
public class BadCustomerRequestException extends RuntimeException {

    private final String message;
}
