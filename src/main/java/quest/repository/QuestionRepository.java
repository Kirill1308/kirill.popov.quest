package quest.repository;

import quest.model.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {
    List<Question> loadQuestionsFromJSON();

    Optional<Question> getQuestionById(int id);

    Optional<String> getCorrectAnswerById(Integer currentQuestionId);
}
