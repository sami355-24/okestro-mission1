package okestro.mission1.exception.custom;

public class BlankException extends IllegalArgumentException {
    public BlankException(String msg) {
        super(msg);
    }
}
