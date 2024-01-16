package quest.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import quest.checker.AnswerChecker;
import quest.context.ApplicationContext;
import quest.model.Question;
import quest.repository.QuestionRepository;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/quiz")
public class QuizServlet extends HttpServlet {
    public static final String QUIZ_PAGE_JSP = "jsp/quizPage.jsp";
    public static final String QUIZ_FINISHED_JSP = "jsp/quizFinished.jsp";
    public static final String FAIL_PAGE_JSP = "jsp/failPage.jsp";
    public static final String CURRENT_QUESTION_ID_ATTRIBUTE = "currentQuestionId";
    private static final QuestionRepository questionRepository = ApplicationContext.getInstanceOf(QuestionRepository.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        Integer questionId = (Integer) session.getAttribute(CURRENT_QUESTION_ID_ATTRIBUTE);
        Optional<Question> question = questionRepository.retrieveQuestion(questionId);

        request.setAttribute("question", question);

        String destination = question.isPresent() ? QUIZ_PAGE_JSP : QUIZ_FINISHED_JSP;
        request.getRequestDispatcher(destination).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        Integer questionId = (Integer) session.getAttribute(CURRENT_QUESTION_ID_ATTRIBUTE);
        String submittedAnswer = request.getParameter("answer");

        questionId = determineQuestionId(questionId);

        boolean isCorrect = new AnswerChecker(questionRepository).isCorrect(questionId, submittedAnswer);
        if (!isCorrect) {
            request.getRequestDispatcher(FAIL_PAGE_JSP).forward(request, response);
            return;
        }

        Optional<Question> nextQuestion = questionRepository.findNextQuestion(questionId);

        if (nextQuestion.isPresent()) {
            session.setAttribute(CURRENT_QUESTION_ID_ATTRIBUTE, nextQuestion.get().getId());
            request.setAttribute("question", nextQuestion);
            request.getRequestDispatcher(QUIZ_PAGE_JSP).forward(request, response);
        } else {
            request.getRequestDispatcher(QUIZ_FINISHED_JSP).forward(request, response);
        }
    }

    private static Integer determineQuestionId(Integer questionId) {
        Optional<Question> question = questionRepository.retrieveQuestion(questionId);
        return question.isPresent() ? question.get().getId() : null;
    }
}
