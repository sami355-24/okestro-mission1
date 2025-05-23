package okestro.mission1.exception.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import okestro.mission1.dto.response.template.MetaData;
import okestro.mission1.dto.response.template.ResponseTemplate;
import okestro.mission1.exception.custom.DuplicateException;
import okestro.mission1.exception.custom.NotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Arrays;

@RestControllerAdvice
@Slf4j
public class CustomHandler {

    @ExceptionHandler(DuplicateException.class)
    private ResponseEntity<ResponseTemplate<Void>> duplicateTagTitleExceptionHandler(DuplicateException e) {
        log.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseTemplate.<Void>builder()
                .metaData(MetaData.ofClientFailure(e.getMessage()))
                .build());
    }

    @ExceptionHandler(NotExistException.class)
    private ResponseEntity<ResponseTemplate<Void>> notExistExceptionHandler(NotExistException e){
        log.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseTemplate.<Void>builder()
                .metaData(MetaData.ofClientFailure(e.getMessage()))
                .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    private ResponseEntity<ResponseTemplate<Void>> validationExceptionHandler(ConstraintViolationException e){
        log.warn(e.getMessage());
        String errorMessage = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse("Validation failed");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseTemplate.<Void>builder()
                .metaData(MetaData.ofClientFailure(errorMessage))
                .build());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    private ResponseEntity<ResponseTemplate<Void>> notFoundExceptionHandler(NoResourceFoundException e){
        log.warn(e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseTemplate.<Void>builder()
                .metaData(MetaData.ofClientFailure("URI를 확인해주세요."))
                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ResponseTemplate<Void>> validationExceptionHandler(MethodArgumentNotValidException e){
        log.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseTemplate.<Void>builder()
                .metaData(MetaData.ofClientFailure("요청 인자 검증에 실패했습니다."))
                .build());
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ResponseTemplate<Void>> exceptionHandler(Exception e) {
        log.error(e.getMessage());
        log.error(Arrays.toString(e.getStackTrace()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseTemplate.<Void>builder()
                .metaData(MetaData.ofServerFailure("서버 내부 로직에 문제가 발생하였습니다."))
                .build());
    }
}
