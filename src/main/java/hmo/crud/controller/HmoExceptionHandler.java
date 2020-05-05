package hmo.crud.controller;

import hmo.crud.controller.response.HmoErrorResponse;
import hmo.crud.domain.exception.HmoAppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import static java.util.Objects.nonNull;

@Slf4j
@RestControllerAdvice
public class HmoExceptionHandler extends ResponseEntityExceptionHandler {

    private static final HttpStatus DEFAULT_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    @ExceptionHandler(HmoAppException.class)
    public ResponseEntity<HmoErrorResponse> hmoAppException(
            HmoAppException e, HttpServletRequest servletRequest) {

        log.error("HmoAppException found processing servletRequest. " + e.getLocalizedMessage());

        HmoErrorResponse response = HmoErrorResponse.builder()
                .developerMessage(e.getDeveloperMessage())
                .userMessage(e.getUserMessage())
                .url(servletRequest.getRequestURI())
                .build();

        return new ResponseEntity<>(response, getHttpStatus(e));
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        log.error("MethodArgumentNotValidException found processing servletRequest. " + ex.getLocalizedMessage());

        StringBuilder sb = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(
                fieldError -> sb.append(fieldError.getDefaultMessage()) );

        HmoErrorResponse response = HmoErrorResponse.builder()
                .developerMessage(sb.toString())
                .build();

        return new ResponseEntity<>(response, status);
    }

    private HttpStatus getHttpStatus(Exception e) {
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(e.getClass(),
                ResponseStatus.class);
        return nonNull(responseStatus) ? responseStatus.value() : DEFAULT_STATUS;
    }
}
