package br.com.wallet.exceptions;

import br.com.wallet.exceptions.errors.ErrorDetails;
import br.com.wallet.exceptions.errors.Violation;
import br.com.wallet.exceptions.errors.ViolationDetails;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice(basePackages = "br.com.user")
public class ErrorHandlerControllerAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ViolationDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();

        List<Violation> errors = result.getFieldErrors().stream()
                .map(violation -> new Violation(violation.getField(), violation.getDefaultMessage()))
                .toList();

        ViolationDetails violationDetails = new ViolationDetails(
                HttpStatus.PRECONDITION_FAILED.value(),
                LocalDateTime.now().toString(),
                "Method argument not valid",
                errors
        );

        return new ResponseEntity<>(violationDetails, HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(MismatchedInputException.class)
    public ResponseEntity<ErrorDetails> handleMismatchedInputException(MismatchedInputException e) {
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                LocalDateTime.now().toString(),
                e.getMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDetails> handleNotFoundException(NotFoundException e) {
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now().toString(),
                e.getMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidStatusException.class)
    public ResponseEntity<ErrorDetails> handleInvalidStatusException(InvalidStatusException e) {
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.NOT_ACCEPTABLE.value(),
                LocalDateTime.now().toString(),
                e.getMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
    }
}
