package quest.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import quest.context.ApplicationContext;
import quest.model.Question;
import quest.repository.QuestionRepository;
import quest.service.AnswerChecker;
import quest.service.QuizAnswerChecker;

import java.io.IOException;

import static java.util.Objects.isNull;

@WebServlet("/quiz")

public class QuizServlet extends HttpServlet {
    private final QuestionRepository questionRepository = ApplicationContext.getInstanceOf(QuestionRepository.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        Question question = questionRepository.loadQuestion(session);
        request.setAttribute("question", question);

        if (!isNull(question)) {
            int currentQuestionId = question.getId();
            int nextQuestionId = currentQuestionId + 1;
            session.setAttribute("currentQuestionId", nextQuestionId);

            RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/quizPage.jsp");
            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/quizFinished.jsp");
            dispatcher.forward(request, response);
        }
    }
}