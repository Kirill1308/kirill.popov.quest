package quest.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import quest.context.ApplicationContext;
import quest.model.Question;
import quest.repository.QuestionRepository;

import java.io.IOException;
import java.util.Optional;

import static java.util.Objects.isNull;

@WebServlet("/quiz")

public class QuizServlet extends HttpServlet {
    private static final QuestionRepository questionRepository = ApplicationContext.getInstanceOf(QuestionRepository.class);
    public static final int FIRST_QUESTION_ID = 1;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        Integer currentQuestionId = (Integer) session.getAttribute("currentQuestionId");
        currentQuestionId = (isNull(currentQuestionId)) ? FIRST_QUESTION_ID : currentQuestionId;

        Optional<Question> question = questionRepository.getQuestionById(currentQuestionId);
        request.setAttribute("question", question);

        if (question.isPresent()) {
            session.setAttribute("currentQuestionId", currentQuestionId + 1);

            request.getRequestDispatcher("jsp/quizPage.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("jsp/quizFinished.jsp").forward(request, response);
        }
    }
}
