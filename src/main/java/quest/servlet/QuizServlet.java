package quest.servlet;

import quest.io.JSONQuestionReader;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import quest.manager.QuizManager;
import quest.model.Question;

import java.io.IOException;
import java.util.List;

@WebServlet("/quiz")
public class QuizServlet extends HttpServlet {
    private final JSONQuestionReader jsonQuestionReader = new JSONQuestionReader();
    private final QuizManager quizManager = new QuizManager();

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
        Question question = quizManager.getNextQuestion(session, currentQuestionIndex);

        if (question != null) {
            request.setAttribute("question", question);

            RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/quizPage.jsp");
            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/quizFinished.jsp");
            dispatcher.forward(request, response);
        }
    }
}