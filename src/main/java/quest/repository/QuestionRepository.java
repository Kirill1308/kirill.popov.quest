package quest.repository;

import jakarta.servlet.http.HttpSession;
import quest.model.Question;

import java.util.List;

public interface QuestionRepository {
    List<Question> loadQuestionsFromJSON();

    Question loadQuestion(HttpSession session);
}