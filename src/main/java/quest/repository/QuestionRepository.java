package quest.repository;

import quest.model.Question;

import java.util.Optional;

public interface QuestionRepository {
    Optional<Question> getQuestionById(Integer questionId);

    Optional<String> getCorrectAnswerById(Integer questionId);

    Optional<Question> findFirstQuestion();

    Optional<Question> retrieveQuestion(Integer questionId);

    Optional<Question> findNextQuestion(Integer questionId);
}
