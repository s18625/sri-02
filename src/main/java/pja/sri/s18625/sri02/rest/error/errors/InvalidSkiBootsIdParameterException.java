package pja.sri.s18625.sri02.rest.error.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static pja.sri.s18625.sri02.Constant.ConstantVariables.INVALID_SKI_BOOTS_ID;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InvalidSkiBootsIdParameterException extends RuntimeException {

    public InvalidSkiBootsIdParameterException() {
        super(INVALID_SKI_BOOTS_ID);
    }
}
