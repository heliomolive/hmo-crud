package hmo.crud.domain.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends HmoAppException {

    @Builder
    public BadRequestException(String userMessage, String developerMessage, Throwable cause) {
        super(userMessage, developerMessage, cause);
    }
}
