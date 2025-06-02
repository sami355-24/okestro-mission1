package okestro.mission1.exception;

public class NotExistException extends IllegalArgumentException {

    public NotExistException(String msg) {
        super(msg);
    }
}
