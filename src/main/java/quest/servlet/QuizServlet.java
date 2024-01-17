package quest.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import quest.checker.AnswerChecker;
import quest.context.ApplicationContext;
import quest.model.Option;
import quest.model.Question;
import quest.repository.QuestionRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Log4j2
@WebServlet("/quiz")
public class QuizServlet extends HttpServlet {
    public static final String QUIZ_PAGE_JSP = "quizPage.jsp";
    public static final String QUIZ_FINISHED_JSP = "quizFinished.jsp";
    public static final String FAIL_PAGE_JSP = "failPage.jsp";
    public static final String CURRENT_QUESTION_ID_ATTRIBUTE = "currentQuestionId";
    public static final String CORRECT_ANSWERS_COUNT_ATTRIBUTE = "correctAnswersCount";
    public static final String TOTAL_QUESTIONS_COUNT_ATTRIBUTE = "totalQuestionsCount";
    private static final QuestionRepository questionRepository = ApplicationContext.getInstanceOf(QuestionRepository.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        log.info("Handling GET request for QuizServlet");

        session.setAttribute(CORRECT_ANSWERS_COUNT_ATTRIBUTE, 0);
        session.setAttribute(TOTAL_QUESTIONS_COUNT_ATTRIBUTE, questionRepository.getQuestions().size());

        Integer questionId = (Integer) session.getAttribute(CURRENT_QUESTION_ID_ATTRIBUTE);
        Optional<Question> question = questionRepository.retrieveQuestion(questionId);

        if (question.isPresent()) {
            List<Option> options = List.of(question.get().getOptions());
            request.setAttribute("question", question);
            request.setAttribute("options", options);

            log.info("Question retrieved: {}", question.get().getText());
        }

        String destination = question.isPresent() ? QUIZ_PAGE_JSP : QUIZ_FINISHED_JSP;
        request.getRequestDispatcher(destination).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        log.info("Handling POST request for QuizServlet");

        Integer questionId = (Integer) session.getAttribute(CURRENT_QUESTION_ID_ATTRIBUTE);
        String submittedAnswer = request.getParameter("answer");

        questionId = determineQuestionId(questionId);
        boolean isCorrect = new AnswerChecker(questionRepository).isCorrect(questionId, submittedAnswer);

        if (isCorrect) {
            handleCorrectAnswer(session);
        } else {
            handleIncorrectAnswer(request, response, session);
            return;
        }

        Optional<Question> nextQuestion = questionRepository.findNextQuestion(questionId);

        if (nextQuestion.isPresent()) {
            handleNextQuestion(request, response, session, nextQuestion);
        } else {
            handleQuizFinished(request, response, session);
        }
    }

    private void handleCorrectAnswer(HttpSession session) {
        Integer correctAnswersCount = (Integer) session.getAttribute(CORRECT_ANSWERS_COUNT_ATTRIBUTE);
        session.setAttribute(CORRECT_ANSWERS_COUNT_ATTRIBUTE, ++correctAnswersCount);
        log.info("Correct answer submitted. Current correct answers count: {}", correctAnswersCount);
    }

    private void handleIncorrectAnswer(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException {
        log.info("Incorrect answer submitted. Redirecting to FAIL_PAGE_JSP");
        request.getRequestDispatcher(FAIL_PAGE_JSP).forward(request, response);
        session.removeAttribute(CURRENT_QUESTION_ID_ATTRIBUTE);
    }

    private void handleNextQuestion(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                    Optional<Question> nextQuestion) throws ServletException, IOException {
        session.setAttribute(CURRENT_QUESTION_ID_ATTRIBUTE, nextQuestion.get().getId());
        request.setAttribute("question", nextQuestion);
        request.getRequestDispatcher(QUIZ_PAGE_JSP).forward(request, response);
        log.info("Next question retrieved: {}", nextQuestion.get().getText());
    }

    private void handleQuizFinished(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException {
        session.removeAttribute(CURRENT_QUESTION_ID_ATTRIBUTE);
        request.getRequestDispatcher(QUIZ_FINISHED_JSP).forward(request, response);
        log.info("Quiz finished. Redirecting to QUIZ_FINISHED_JSP");
    }

    private Integer determineQuestionId(Integer questionId) {
        Optional<Question> question = questionRepository.retrieveQuestion(questionId);
        return question.isPresent() ? question.get().getId() : null;
    }
}
