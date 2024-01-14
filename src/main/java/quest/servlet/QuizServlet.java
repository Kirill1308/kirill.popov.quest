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
    public static final String QUIZ_PAGE_JSP = "jsp/quizPage.jsp";
    public static final String QUIZ_FINISHED_JSP = "jsp/quizFinished.jsp";
    public static final String FAIL_PAGE_JSP = "jsp/failPage.jsp";
    public static final String CURRENT_QUESTION_ID_ATTRIBUTE = "currentQuestionId";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        Integer currentQuestionId = (Integer) session.getAttribute(CURRENT_QUESTION_ID_ATTRIBUTE);
        currentQuestionId = (isNull(currentQuestionId)) ? FIRST_QUESTION_ID : currentQuestionId;

        Optional<Question> question = questionRepository.getQuestionById(currentQuestionId);
        request.setAttribute("question", question);

        if (question.isPresent()) {
            request.getRequestDispatcher(QUIZ_PAGE_JSP).forward(request, response);
        } else {
            request.getRequestDispatcher(QUIZ_FINISHED_JSP).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        Integer currentQuestionId = (Integer) session.getAttribute(CURRENT_QUESTION_ID_ATTRIBUTE);
        currentQuestionId = (isNull(currentQuestionId)) ? FIRST_QUESTION_ID : currentQuestionId;

        Optional<String> correctAnswer = questionRepository.getCorrectAnswerById(currentQuestionId);
        String submittedAnswer = request.getParameter("answer");

        boolean isCorrect = correctAnswer.isPresent() && correctAnswer.get().equals(submittedAnswer);

        if (isCorrect) {
            session.setAttribute(CURRENT_QUESTION_ID_ATTRIBUTE, currentQuestionId + 1);
            Optional<Question> nextQuestion = questionRepository.getQuestionById(currentQuestionId + 1);
            request.setAttribute("question", nextQuestion);
            request.getRequestDispatcher(QUIZ_PAGE_JSP).forward(request, response);
        } else {
            request.getRequestDispatcher(FAIL_PAGE_JSP).forward(request, response);
        }
    }
}
