package cl.ntt.userapi.user_api.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ServiceException extends ResponseStatusException {

    public ServiceException(String reason) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
    }

    public ServiceException(String reason, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason, cause);
    }

}
