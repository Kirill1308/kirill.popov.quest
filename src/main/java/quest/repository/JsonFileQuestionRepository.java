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

public class JsonFileQuestionRepository implements QuestionRepository {
    private static final String QUESTIONS_JSON = "/questions.json";
    private final Gson gson = new Gson();
    private final List<Question> questions;

    public JsonFileQuestionRepository() {
        this.questions = loadQuestionsFromJSON();
    }

    @Override
    public List<Question> loadQuestionsFromJSON() {
        try (Reader reader = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(QUESTIONS_JSON)))) {
            return gson.fromJson(reader, new TypeToken<List<Question>>() {
            }.getType());
        } catch (Exception e) {
            throw new JsonFileReadingException("Error reading questions from JSON file.", e);
        }
    }

    @Override
    public Optional<Question> getQuestionById(int id) {
        return questions.stream()
                .filter(question -> question.getId() == id)
                .findFirst();
    }
}