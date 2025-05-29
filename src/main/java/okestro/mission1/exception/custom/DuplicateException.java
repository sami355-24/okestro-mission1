package okestro.mission1.exception.custom;

import org.springframework.dao.DuplicateKeyException;

public class DuplicateException extends DuplicateKeyException {

    public DuplicateException(String msg) {
        super(msg);
    }

}
