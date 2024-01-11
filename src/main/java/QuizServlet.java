import io.JSONQuestionReader;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.QuestionService;
import service.QuizManager;
import model.Question;

import java.io.IOException;
import java.util.List;

@WebServlet("/quiz")
public class QuizServlet extends HttpServlet {
//    private final JSONQuestionReader jsonQuestionReader = new JSONQuestionReader();

    private final QuestionService questionService = ApplicationContext.getInstanceOf(QuestionService.class);

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



