package exceptions;

public class DeleteFromCellException extends Exception {
    public DeleteFromCellException() {
    }

    public DeleteFromCellException(String message) {
        super(message);
    }

    public DeleteFromCellException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeleteFromCellException(Throwable cause) {
        super(cause);
    }

    public DeleteFromCellException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
