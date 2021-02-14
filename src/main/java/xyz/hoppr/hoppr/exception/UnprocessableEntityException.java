package xyz.hoppr.hoppr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends ApiException {

    public UnprocessableEntityException(String message) {
        super(message);
    }
}
