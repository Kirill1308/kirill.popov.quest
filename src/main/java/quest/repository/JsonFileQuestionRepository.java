package quest.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import quest.exception.JsonFileReadingException;
import quest.model.Question;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.isNull;

public class JsonFileQuestionRepository implements QuestionRepository {
    private static final String QUESTIONS_JSON = "/questions.json";
    private final Gson gson = new Gson();
    private final List<Question> questions;

    public JsonFileQuestionRepository() {
        this.questions = loadQuestionsFromJSON();
    }

    private List<Question> loadQuestionsFromJSON() {
        try (Reader reader = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(QUESTIONS_JSON)))) {
            return gson.fromJson(reader, new TypeToken<List<Question>>() {
            }.getType());
        } catch (Exception e) {
            throw new JsonFileReadingException("Error reading questions from JSON file.", e);
        }
    }

    @Override
    public Optional<Question> retrieveQuestion(Integer questionId) {
        return isNull(questionId) ? findFirstQuestion() : getQuestionById(questionId);
    }

    public Optional<Question> findFirstQuestion() {
        return questions.stream().findFirst();
    }

    @Override
    public Optional<Question> getQuestionById(Integer questionId) {
        if (isNull(questionId)) {
            return Optional.empty();
        }
        return questions.stream()
                .filter(question -> question.getId() == questionId)
                .findFirst();
    }

    @Override
    public Optional<String> getCorrectAnswerById(Integer questionId) {
        return questions.stream()
                .filter(question -> question.getId() == questionId)
                .map(Question::getAnswer)
                .findFirst();
    }

    @Override
    public Optional<Question> findNextQuestion(Integer questionId) {
        if (isNull(questionId)) {
            return Optional.empty();
        }
        return questions.stream()
                .filter(question -> question.getId() == questionId + 1)
                .findFirst();
    }
}
