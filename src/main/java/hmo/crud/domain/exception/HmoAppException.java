package hmo.crud.domain.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HmoAppException extends RuntimeException {
    private String userMessage;
    private String developerMessage;

    public HmoAppException() {}

    public HmoAppException(String userMessage, String developerMessage, Throwable cause) {
        super(developerMessage, cause);
        this.userMessage = userMessage;
        this.developerMessage = developerMessage;
    }
}
