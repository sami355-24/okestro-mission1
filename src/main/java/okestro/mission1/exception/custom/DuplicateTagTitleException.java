package okestro.mission1.exception.custom;

import org.springframework.dao.DuplicateKeyException;

public class DuplicateTagTitleException extends DuplicateKeyException {

    public DuplicateTagTitleException(String msg) {
        super(msg);
    }

}
