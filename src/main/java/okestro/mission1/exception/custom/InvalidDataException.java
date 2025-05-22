package okestro.mission1.exception.custom;

public class InvalidDataException extends IllegalArgumentException {
    public InvalidDataException(String msg) {
        super(msg);
    }
}
