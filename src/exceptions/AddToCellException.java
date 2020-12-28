package exceptions;

public class AddToCellException extends Exception {
    public AddToCellException() {
    }

    public AddToCellException(String message) {
        super(message);
    }

    public AddToCellException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddToCellException(Throwable cause) {
        super(cause);
    }

    public AddToCellException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
