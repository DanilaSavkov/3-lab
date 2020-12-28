package exceptions;

public class MovingException extends Exception {
    public MovingException() {
    }

    public MovingException(String message) {
        super(message);
    }

    public MovingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovingException(Throwable cause) {
        super(cause);
    }

    public MovingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
