package pl.ferdynand.calendar.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.ferdynand.calendar.ui.model.response.ErrorMessage;

import java.io.IOException;
import java.util.Date;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
        String errorMessageDescription = ex.getLocalizedMessage();
        if(errorMessageDescription == null) {
            errorMessageDescription = ex.toString();
        }
        ErrorMessage handleMessage = new ErrorMessage(new Date(), errorMessageDescription);
        return new ResponseEntity<>(
                handleMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(value = DateFormatException.class)
    public ResponseEntity<Object> handlerDateFormatException(DateFormatException exN, WebRequest request) {
        String errorMessageDescription = exN.getLocalizedMessage();
        if(errorMessageDescription == null)
            errorMessageDescription = exN.toString();

        ErrorMessage handler = new ErrorMessage(new Date(), errorMessageDescription);

        return new ResponseEntity<>(handler, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> handleNullPointerException (NullPointerException exN, WebRequest request) {
        String errorMessageDescription = exN.getLocalizedMessage();
        if(errorMessageDescription == null)
            errorMessageDescription = exN.toString();

        ErrorMessage handler = new ErrorMessage(new Date(), errorMessageDescription);

        return new ResponseEntity<>(handler, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { IOException.class})
    public ResponseEntity<Object> handleIOException (IOException ex, WebRequest request) {
        String errorMessageDescription = ex.getLocalizedMessage();
        if(errorMessageDescription == null)
            errorMessageDescription = ex.toString();

        ErrorMessage handler = new ErrorMessage(new Date(), errorMessageDescription);

        return new ResponseEntity<>(handler, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = NumberFormatException.class)
    public ResponseEntity<Object> handleNumberFormatException(NumberFormatException exN, WebRequest request) {
        String errorMessageDescription = exN.getLocalizedMessage();
        if(errorMessageDescription == null)
            errorMessageDescription = exN.toString();

        ErrorMessage handler = new ErrorMessage(new Date(), errorMessageDescription);

        return new ResponseEntity<>(handler, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
