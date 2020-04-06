package hmo.crud.domain.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends HmoAppException {

    @Builder
    public NotFoundException(String userMessage, String developerMessage, Throwable cause) {
        super(userMessage, developerMessage, cause);
    }
}
