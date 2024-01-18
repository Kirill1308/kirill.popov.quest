package quest.checker;

import lombok.AllArgsConstructor;
import quest.repository.QuestionRepository;

import java.util.Optional;

@AllArgsConstructor
public final class AnswerChecker {
    private final QuestionRepository questionRepository;
    public boolean isCorrect(Integer questionId, String submittedAnswer) {
        Optional<String> correctAnswer = questionRepository.getCorrectAnswerById(questionId);
        return correctAnswer.isPresent() && correctAnswer.get().equals(submittedAnswer);
    }
}
