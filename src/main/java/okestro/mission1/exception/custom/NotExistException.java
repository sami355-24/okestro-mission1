package okestro.mission1.exception.custom;

public class NotExistException extends IllegalArgumentException {

    public NotExistException(String msg) {
        super(msg);
    }
}
