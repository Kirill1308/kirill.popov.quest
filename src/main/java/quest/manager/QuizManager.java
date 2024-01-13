package quest.manager;

import jakarta.servlet.http.HttpSession;
import quest.model.Question;

import java.util.List;

public class QuizManager {

    public Question getNextQuestion(HttpSession session, int currentQuestionIndex) {
        List<Question> questions = (List<Question>) session.getAttribute("questions");

        if (questions == null || currentQuestionIndex >= questions.size()) {
            return null; // No more questions
        }

        Question nextQuestion = questions.get(currentQuestionIndex);
        session.setAttribute("currentQuestionIndex", currentQuestionIndex + 1);

        return nextQuestion;
    }

    public boolean checkAnswer(Question question, String answer) {
        return question.getAnswer().equals(answer);
    }
}