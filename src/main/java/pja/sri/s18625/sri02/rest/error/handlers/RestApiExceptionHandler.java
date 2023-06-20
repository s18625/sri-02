package pja.sri.s18625.sri02.rest.error.handlers;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pja.sri.s18625.sri02.dto.ErrorMessage;
import pja.sri.s18625.sri02.rest.error.errors.InvalidBuyerIdParameterException;
import pja.sri.s18625.sri02.rest.error.errors.InvalidSkiBootsIdParameterException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        Map<String, List<String>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(FieldError::getField,
                        Collectors.mapping(DefaultMessageSourceResolvable::getDefaultMessage, Collectors.toList())));

        ErrorMessage errorMessage = ErrorMessage.builder()
                .httpStatus(badRequest)
                .errors(errors)
                .build();

        return new ResponseEntity<>(errorMessage, badRequest);
    }

    @ExceptionHandler({InvalidSkiBootsIdParameterException.class, InvalidBuyerIdParameterException.class})
    protected ResponseEntity<Object> handleInvalidParameterException(RuntimeException ex) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ErrorMessage errorMessage = ErrorMessage.builder()
                .httpStatus(badRequest)
                .error(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorMessage, badRequest);
    }


}
