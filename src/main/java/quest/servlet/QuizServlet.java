package quest.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import quest.context.ApplicationContext;
import quest.context.RequestHandlerContext;
import quest.model.Question;
import quest.repository.QuestionRepository;
import quest.service.QuizService;

import java.io.IOException;
import java.util.Optional;

@Log4j2
@WebServlet("/quiz")
public class QuizServlet extends HttpServlet {
    private static final QuestionRepository questionRepository = ApplicationContext.getInstanceOf(QuestionRepository.class);
    private static final QuizService quizService = ApplicationContext.getInstanceOf(QuizService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.info("Handling GET request for QuizServlet");
        HttpSession session = request.getSession();
        RequestHandlerContext context = new RequestHandlerContext(request, response, session);

        quizService.setSessionAttributes(context);

        Optional<Question> question = quizService.retrieveQuestion(context);
        if (question.isPresent()) {
            quizService.setQuestionAttributes(context, question.get());
            log.info("Question retrieved: {}", question.get().getText());
        }

        String nextPage = quizService.determineNextPage(question);
        quizService.forwardToPage(context, nextPage);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.info("Handling POST request for QuizServlet");
        HttpSession session = request.getSession();
        RequestHandlerContext context = new RequestHandlerContext(request, response, session);

        String submittedAnswer = request.getParameter("answer");
        Optional<Question> nextQuestion = questionRepository.findNextQuestion(context.getQuestionId());

        Integer questionId = nextQuestion.map(Question::getId).orElse(context.getQuestionId());
        context.setQuestionId(questionId);

        boolean isAnswerCorrect = questionRepository.checkAnswer(context.getQuestionId(), submittedAnswer);
        quizService.handleAnswer(isAnswerCorrect, context);
    }
}
