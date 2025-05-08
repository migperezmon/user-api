package cl.ntt.userapi.user_api.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends ResponseStatusException {
    public ForbiddenException(String reason) {
        super(HttpStatus.FORBIDDEN, reason);
    }

    public ForbiddenException(String reason, Throwable cause) {
        super(HttpStatus.FORBIDDEN, reason, cause);
    }

}
