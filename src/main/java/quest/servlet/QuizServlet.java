package quest.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import quest.repository.JsonFileQuestionRepository;
import quest.model.Question;

import java.io.IOException;

import static java.util.Objects.isNull;

@WebServlet("/quiz")
public class QuizServlet extends HttpServlet {
    public static final String CURRENT_QUESTION_ID = "currentQuestionId";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        JsonFileQuestionRepository jsonFileQuestionRepository = new JsonFileQuestionRepository();

        Question question = jsonFileQuestionRepository.loadNextQuestion(session);

        if (!isNull(question)) {
            request.setAttribute("question", question);

            String nextQuestionId = jsonFileQuestionRepository.getNextQuestionId(session);
            session.setAttribute(CURRENT_QUESTION_ID, nextQuestionId);

            RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/quizPage.jsp");
            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/quizFinished.jsp");
            dispatcher.forward(request, response);
        }
    }
}