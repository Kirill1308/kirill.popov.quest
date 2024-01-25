package quest.exception;

public class CommandNotFoundException extends RuntimeException {
    public CommandNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandNotFoundException(String message) {
        super(message);
    }
}
