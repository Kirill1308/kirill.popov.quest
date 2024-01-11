package repository;

import model.Question;
import model.QuestionType;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {

    List<Question> findAll();

    Optional<Question> findById(String id);

    List<Question> findByQuestionType(QuestionType type);
}
