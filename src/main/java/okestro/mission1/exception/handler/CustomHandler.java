package okestro.mission1.exception.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import okestro.mission1.dto.controller.response.template.MetaData;
import okestro.mission1.dto.controller.response.template.ResponseTemplate;
import okestro.mission1.exception.custom.DuplicateException;
import okestro.mission1.exception.custom.InvalidDataException;
import okestro.mission1.exception.custom.NotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Arrays;

import static okestro.mission1.util.Message.*;

@RestControllerAdvice
@Slf4j
public class CustomHandler {

    @ExceptionHandler({DuplicateException.class, NotExistException.class, InvalidDataException.class})
    private ResponseEntity<ResponseTemplate<Void>> handleBusinessLogicException(Exception e) {
        log.warn(e.getMessage());
        return createBadRequestResponse(e, e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    private ResponseEntity<ResponseTemplate<Void>> handleConstraintViolationException(ConstraintViolationException e) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse(ERROR_CONSTRAINT_VIOLATION.getMessage());

        return createBadRequestResponse(e, errorMessage);
    }

    @ExceptionHandler({NoResourceFoundException.class, NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    private ResponseEntity<ResponseTemplate<Void>> handleResourceNotFoundException(Exception e) {
        return createBadRequestResponse(e, ERROR_NOT_FOUND_URI.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ResponseTemplate<Void>> handleMethodArgumentValidationException(MethodArgumentNotValidException e) {
        return createBadRequestResponse(e, ERROR_VALIDATE_ARGUMENT.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    private ResponseEntity<ResponseTemplate<Void>> handleUnexpectedException(Exception e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseTemplate.<Void>builder()
                .metaData(MetaData.ofServerFailure(ERROR_INTERNAL_SERVER.getMessage()))
                .build());
    }

    private ResponseEntity<ResponseTemplate<Void>> createBadRequestResponse(Exception e, String errorMessage) {
        log.warn(e.getMessage());
        log.warn(Arrays.toString(e.getStackTrace()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseTemplate.<Void>builder()
                .metaData(MetaData.ofClientFailure(errorMessage))
                .build());
    }

}
