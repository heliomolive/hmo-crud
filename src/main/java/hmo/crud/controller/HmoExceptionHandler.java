package hmo.crud.controller;

import hmo.crud.controller.response.HmoErrorResponse;
import hmo.crud.domain.exception.HmoAppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Objects.nonNull;

@Slf4j
@RestControllerAdvice
public class HmoExceptionHandler extends ResponseEntityExceptionHandler {

    private static final HttpStatus DEFAULT_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    @ExceptionHandler(HmoAppException.class)
    public ResponseEntity<HmoErrorResponse> hmoAppException(HmoAppException e,
                                                            HttpServletRequest servletRequest,
                                                            HttpServletResponse servletResponse) {
        log.error("Error processing servletRequest.", e);

        HmoErrorResponse response = HmoErrorResponse.builder()
                .developerMessage(e.getDeveloperMessage())
                .userMessage(e.getUserMessage())
                .url(servletRequest.getRequestURI())
                .build();

        return new ResponseEntity<>(response, getHttpStatus(e));
    }

    private HttpStatus getHttpStatus(Exception e) {
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(e.getClass(),
                ResponseStatus.class);
        return nonNull(responseStatus) ? responseStatus.value() : DEFAULT_STATUS;
    }
}
