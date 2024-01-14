package quest.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpSession;
import quest.exception.JsonFileReadingException;
import quest.exception.QuestionLoadingException;
import quest.model.Question;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

public class JsonFileQuestionRepository implements QuestionRepository {
    private static final String QUESTIONS_JSON = "/questions.json";
    public static final String CURRENT_QUESTION_ID_ATTRIBUTE = "currentQuestionId";
    public static final String QUESTIONS_ATTRIBUTE = "questions";
    private final Gson gson = new Gson();
    private List<Question> questions;

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
    @SuppressWarnings("unchecked")
    public Question loadQuestion(HttpSession session) {
        try {
           questions = (List<Question>) session.getAttribute(QUESTIONS_ATTRIBUTE);

            if (isNull(questions) || questions.isEmpty()) {
                questions = loadQuestionsFromJSON();
                session.setAttribute(QUESTIONS_ATTRIBUTE, questions);
                session.setAttribute(CURRENT_QUESTION_ID_ATTRIBUTE, questions.get(0).getId());
            }

            int currentQuestionId = (int) session.getAttribute(CURRENT_QUESTION_ID_ATTRIBUTE);
            return getQuestionById(currentQuestionId);
        } catch (Exception e) {
            throw new QuestionLoadingException("Error while loading question. ", e);
        }
    }

    private Question getQuestionById(int id) {
        return questions.stream()
                .filter(question -> question.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Question> getQuestions() {
        return Collections.unmodifiableList(questions);
    }
}