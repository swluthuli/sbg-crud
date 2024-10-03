package za.co.sbg.demo.handler.exception;

public class ConstraintViolationException  extends RuntimeException {
    public ConstraintViolationException(String message) {
        super(message);
    }
}
