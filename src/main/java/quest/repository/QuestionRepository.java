package quest.repository;

import jakarta.servlet.http.HttpSession;
import quest.model.Question;

import java.util.List;

public interface QuestionRepository {
    List<Question> loadQuestionsFromJSON();

    Question loadNextQuestion(HttpSession session);

    String getNextQuestionId(HttpSession session);

    Question getQuestionById(List<Question> questions, String id);

    String getNextQuestionId(List<Question> questions, String currentQuestionId);
}