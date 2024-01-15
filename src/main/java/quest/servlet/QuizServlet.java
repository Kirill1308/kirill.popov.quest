package quest.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import quest.checker.AnswerChecker;
import quest.constant.QuizConstant;
import quest.context.ApplicationContext;
import quest.model.Question;
import quest.repository.QuestionRepository;

import java.io.IOException;
import java.util.Optional;

import static java.util.Objects.isNull;
import static quest.constant.QuizConstant.CURRENT_QUESTION_ID_ATTRIBUTE;
import static quest.constant.QuizConstant.FIRST_QUESTION_ID;
import static quest.constant.QuizConstant.QUIZ_FINISHED_JSP;
import static quest.constant.QuizConstant.QUIZ_PAGE_JSP;

@WebServlet("/quiz")
public class QuizServlet extends HttpServlet {
    private static final QuestionRepository questionRepository = ApplicationContext.getInstanceOf(QuestionRepository.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        Integer questionId = (Integer) session.getAttribute(CURRENT_QUESTION_ID_ATTRIBUTE);
        questionId = (isNull(questionId)) ? FIRST_QUESTION_ID : questionId;

        Optional<Question> question = questionRepository.getQuestionById(questionId);
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

        Integer questionId = (Integer) session.getAttribute(CURRENT_QUESTION_ID_ATTRIBUTE);
        questionId = (isNull(questionId)) ? FIRST_QUESTION_ID : questionId;

        Optional<String> correctAnswer = questionRepository.getCorrectAnswerById(questionId);
        String submittedAnswer = request.getParameter("answer");

        boolean isCorrect = AnswerChecker.isAnswerCorrect(correctAnswer, submittedAnswer);

        if (isCorrect) {
            session.setAttribute(CURRENT_QUESTION_ID_ATTRIBUTE, questionId + 1);
            Optional<Question> nextQuestion = questionRepository.getQuestionById(questionId + 1);

            request.setAttribute("question", nextQuestion);
            request.getRequestDispatcher(QUIZ_PAGE_JSP).forward(request, response);
        } else {
            request.getRequestDispatcher(QuizConstant.FAIL_PAGE_JSP).forward(request, response);
        }
    }
}
