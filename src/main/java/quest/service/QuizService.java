package quest.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import quest.model.Option;
import quest.model.Question;
import quest.repository.QuestionRepository;
import quest.context.RequestHandlerContext;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Log4j2
@AllArgsConstructor
public class QuizService {
    public static final String QUIZ_PAGE_JSP = "quizPage.jsp";
    public static final String QUIZ_FINISHED_JSP = "completedQuiz.jsp";
    public static final String FAIL_PAGE_JSP = "gameOver.jsp";
    public static final String CURRENT_QUESTION_ID_ATTRIBUTE = "currentQuestionId";
    public static final String CORRECT_ANSWERS_COUNT_ATTRIBUTE = "correctAnswersCount";
    public static final String TOTAL_QUESTIONS_COUNT_ATTRIBUTE = "totalQuestionsCount";

    private final QuestionRepository questionRepository;

    public void setSessionAttributes(RequestHandlerContext context) {
        context.getSession().setAttribute(CORRECT_ANSWERS_COUNT_ATTRIBUTE, 0);
        context.getSession().setAttribute(TOTAL_QUESTIONS_COUNT_ATTRIBUTE, questionRepository.getQuestions().size());
    }

    public void setQuestionAttributes(HttpServletRequest request, Question question) {
        List<Option> options = List.of(question.getOptions());
        request.setAttribute("question", question);
        request.setAttribute("options", options);
    }

    public Optional<Question> retrieveQuestion(RequestHandlerContext context) {
        Integer questionId = (Integer) context.getSession().getAttribute(CURRENT_QUESTION_ID_ATTRIBUTE);
        return questionRepository.retrieveQuestion(questionId);
    }

    public void handleAnswer(boolean isAnswerCorrect, RequestHandlerContext context) throws ServletException, IOException {
        if (isAnswerCorrect) {
            handleCorrectAnswer(context.getSession());
        } else {
            handleIncorrectAnswer(context.getSession());
            forwardToPage(context, FAIL_PAGE_JSP);
            return;
        }
        handleNextOrFinishedState(context, context.getQuestionId());
    }

    private void handleCorrectAnswer(HttpSession session) {
        Integer correctAnswersCount = (Integer) session.getAttribute(CORRECT_ANSWERS_COUNT_ATTRIBUTE);
        session.setAttribute(CORRECT_ANSWERS_COUNT_ATTRIBUTE, ++correctAnswersCount);
        log.info("Correct answer submitted. Current correct answers count: {}", correctAnswersCount);
    }

    private void handleIncorrectAnswer(HttpSession session) {
        log.info("Incorrect answer submitted. Redirecting to FAIL_PAGE_JSP");
        session.removeAttribute(CURRENT_QUESTION_ID_ATTRIBUTE);
    }

    private void handleNextOrFinishedState(RequestHandlerContext context, Integer questionId) throws ServletException, IOException {
        Optional<Question> nextQuestion = questionRepository.findNextQuestion(questionId);
        if (nextQuestion.isPresent()) {
            handleNextQuestion(context, nextQuestion.get());
        } else {
            handleQuizFinished(context);
        }
    }

    private void handleNextQuestion(RequestHandlerContext context, Question nextQuestion) throws ServletException, IOException {
        context.getSession().setAttribute(CURRENT_QUESTION_ID_ATTRIBUTE, nextQuestion.getId());
        context.getReq().setAttribute("question", nextQuestion);
        forwardToPage(context, QUIZ_PAGE_JSP);
        log.info("Next question retrieved: {}", nextQuestion.getText());
    }

    private void handleQuizFinished(RequestHandlerContext context) throws ServletException, IOException {
        forwardToPage(context, QUIZ_FINISHED_JSP);
        context.getSession().invalidate();
        log.info("Quiz finished. Redirecting to QUIZ_FINISHED_JSP");
    }

    public void forwardToPage(RequestHandlerContext context, String page) throws ServletException, IOException {
        context.getReq().getRequestDispatcher(page).forward(context.getReq(), context.getRes());
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public String determineNextPage(Optional<Question> question) {
        return question.isPresent() ? QUIZ_PAGE_JSP : QUIZ_FINISHED_JSP;
    }
}
