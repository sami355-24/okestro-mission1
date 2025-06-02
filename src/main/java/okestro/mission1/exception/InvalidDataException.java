package okestro.mission1.exception;

public class InvalidDataException extends IllegalArgumentException {
    public InvalidDataException(String msg) {
        super(msg);
    }
}
