package repository;

import model.Question;
import model.QuestionType;

import java.util.List;
import java.util.Optional;

public class SQLQuestionRepository implements QuestionRepository{
    @Override
    public List<Question> findAll() {
        return null;
    }

    @Override
    public Optional<Question> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Question> findByQuestionType(QuestionType type) {
        return null;
    }
}
