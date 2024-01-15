package quest.checker;

import java.util.Optional;

public class AnswerChecker {

    private AnswerChecker() {
    }
    public static boolean isAnswerCorrect(Optional<String> correctAnswer, String submittedAnswer) {
        return correctAnswer.isPresent() && correctAnswer.get().equals(submittedAnswer);
    }
}
