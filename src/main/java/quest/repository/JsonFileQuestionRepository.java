package quest.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpSession;
import quest.exception.JsonFileReadingException;
import quest.model.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

public class JsonFileQuestionRepository implements QuestionRepository {
    private static final String QUESTIONS_JSON = "/questions.json";
    public static final String CURRENT_QUESTION_ID = "currentQuestionId";
    public static final String QUESTIONS = "questions";
    private final Gson gson = new Gson();

    @Override
    public List<Question> loadQuestionsFromJSON() {
        try (Reader reader = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(QUESTIONS_JSON)))) {
            return gson.fromJson(reader, new TypeToken<List<Question>>() {
            }.getType());
        } catch (IOException e) {
            throw new JsonFileReadingException("Error reading questions from JSON file.", e);
        }
    }

    @Override
    public Question loadNextQuestion(HttpSession session) {
        List<Question> questions = getOrCreateQuestions(session);
        String currentQuestionId = (String) session.getAttribute(CURRENT_QUESTION_ID);
        return getQuestionById(questions, currentQuestionId);
    }

    @Override
    public String getNextQuestionId(HttpSession session) {
        List<Question> questions = getOrCreateQuestions(session);
        String currentQuestionId = (String) session.getAttribute(CURRENT_QUESTION_ID);
        return getNextQuestionId(questions, currentQuestionId);
    }
    @Override
    public String getNextQuestionId(List<Question> questions, String currentQuestionId) {
        int currentQuestionIndex = questions.indexOf(getQuestionById(questions, currentQuestionId));
        int nextQuestionIndex = currentQuestionIndex + 1;

        return (nextQuestionIndex < questions.size()) ? questions.get(nextQuestionIndex).id() : null;
    }

    @Override
    public Question getQuestionById(List<Question> questions, String id) {
        return questions.stream()
                .filter(question -> question.id().equals(id))
                .findFirst()
                .orElse(null);
    }

    private List<Question> getOrCreateQuestions(HttpSession session) {
        @SuppressWarnings("unchecked")
        List<Question> questions = (List<Question>) session.getAttribute(QUESTIONS);

        if (isNull(questions) || questions.isEmpty()) {
            questions = loadQuestionsFromJSON();
            session.setAttribute(QUESTIONS, questions);
            session.setAttribute(CURRENT_QUESTION_ID, questions.get(0).id());
        }

        return questions;
    }
}