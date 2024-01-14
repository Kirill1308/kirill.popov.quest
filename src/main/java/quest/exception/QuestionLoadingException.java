package quest.exception;

public class QuestionLoadingException extends RuntimeException {
    public QuestionLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}