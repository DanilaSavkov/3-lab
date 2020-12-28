package exceptions;

public class EatingException extends Exception {
    public EatingException() {
    }

    public EatingException(String message) {
        super(message);
    }

    public EatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public EatingException(Throwable cause) {
        super(cause);
    }

    public EatingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
