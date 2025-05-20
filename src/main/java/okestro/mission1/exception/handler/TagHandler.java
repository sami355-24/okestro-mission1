package okestro.mission1.exception.handler;

import lombok.extern.slf4j.Slf4j;
import okestro.mission1.dto.response.template.MetaData;
import okestro.mission1.dto.response.template.ResponseTemplate;
import okestro.mission1.exception.custom.DuplicateException;
import okestro.mission1.exception.custom.NotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class TagHandler {

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
}
