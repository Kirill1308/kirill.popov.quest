import io.JSONQuestionReader;
import manager.QuizManager;
import model.Question;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/QuizServlet")
public class QuizServlet extends HttpServlet {
    private final JSONQuestionReader jsonQuestionReader = new JSONQuestionReader();

    public QuizServlet() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        @SuppressWarnings("unchecked")
        List<Question> questions = (List<Question>) session.getAttribute("questions");

        if (questions == null || questions.isEmpty()) {
            questions = jsonQuestionReader.readQuestionsFromJSON();
            session.setAttribute("questions", questions);
            session.setAttribute("currentQuestionIndex", 0);
        }

        int currentQuestionIndex = (int) session.getAttribute("currentQuestionIndex");

        QuizManager quizManager = new QuizManager(questions);
        Question question = quizManager.getQuestion(currentQuestionIndex);

        if (question != null) {
            session.setAttribute("currentQuestionIndex", currentQuestionIndex + 1);
            request.setAttribute("question", question);
            RequestDispatcher dispatcher = request.getRequestDispatcher("QuizPage.jsp");
            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("QuizFinished.jsp");
            dispatcher.forward(request, response);
        }
    }
}


// TODO: 1. Add doPost method
// TODO: 2. Add QuizManager class
// TODO: 3. Add QuizManager instance to QuizServlet
// TODO: 4. Add method to QuizManager to check answer
// TODO: 5. Add method to QuizManager to get next question
// TODO: 6. Add method to QuizManager to get previous question

