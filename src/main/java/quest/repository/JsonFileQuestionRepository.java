package quest.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import quest.exception.JsonFileIOException;
import quest.model.Question;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.isNull;

@Getter
@Log4j2
public class JsonFileQuestionRepository implements QuestionRepository {
    private static final String QUESTIONS_JSON = "/questions.json";
    private final Gson gson = new Gson();
    private final List<Question> questions;

    public JsonFileQuestionRepository() {
        this.questions = loadQuestionsFromJSON();
    }

    private List<Question> loadQuestionsFromJSON() {
        try (Reader reader = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(QUESTIONS_JSON)))) {
            List<Question> loadedQuestions = gson.fromJson(reader, new TypeToken<List<Question>>() {
            }.getType());
            log.info("Loaded {} questions from JSON file", loadedQuestions.size());
            return loadedQuestions;
        } catch (Exception e) {
            log.error("Error reading questions from JSON file.", e);
            throw new JsonFileIOException("Error reading questions from JSON file.", e);
        }
    }

    @Override
    public Optional<Question> retrieveQuestion(Integer questionId) {
        return isNull(questionId) ? findFirstQuestion() : getQuestionById(questionId);
    }

    @Override
    public Optional<Question> getQuestionById(Integer questionId) {
        log.info("Retrieving question by ID: {}", questionId);
        return questions.stream()
                .filter(question -> question.getId() == questionId)
                .findFirst();
    }

    @Override
    public Optional<String> getCorrectAnswerById(Integer questionId) {
        log.info("Retrieving correct answer by question ID: {}", questionId);
        return questions.stream()
                .filter(question -> question.getId() == questionId)
                .map(Question::getAnswer)
                .findFirst();
    }

    @Override
    public Optional<Question> findNextQuestion(Integer questionId) {
        if (isNull(questionId)) {
            log.info("Finding the first question.");
            return findFirstQuestion();
        }

        log.info("Finding the next question after ID: {}", questionId);
        return questions.stream()
                .filter(question -> question.getId() == questionId + 1)
                .findFirst();
    }

    private Optional<Question> findFirstQuestion() {
        log.info("Retrieving the first question.");
        return questions.stream().findFirst();
    }
}
