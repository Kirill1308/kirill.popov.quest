package quest.service;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import quest.model.Option;
import quest.model.Question;
import quest.repository.QuestionRepository;
import quest.context.RequestHandlerContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {
    @Mock
    private QuestionRepository mockQuestionRepository;
    private QuizService mockQuizService;
    @Mock
    private Question mockQuestion;
    @Mock
    private Option mockOption;
    @Mock
    private RequestHandlerContext mockContext;
    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpServletResponse mockResponse;
    @Mock
    private HttpSession mockSession;
    @Mock
    private RequestDispatcher mockDispatcher;

    @BeforeEach
    void setUp() {
        mockQuizService = new QuizService(mockQuestionRepository);
    }

    @Test
    void setSessionAttributes() {
        when(mockContext.getSession()).thenReturn(mockSession);
        when(mockQuestionRepository.getQuestions()).thenReturn(List.of(mockQuestion, mockQuestion, mockQuestion));

        mockQuizService.setSessionAttributes(mockContext);

        verify(mockSession).setAttribute(QuizService.CORRECT_ANSWERS_COUNT_ATTRIBUTE, 0);
        verify(mockSession).setAttribute(QuizService.TOTAL_QUESTIONS_COUNT_ATTRIBUTE, 3);
    }

    @Test
    void setQuestionAttributes() {
        Option[] mockOptions = {mockOption, mockOption};

        when(mockQuestion.getOptions()).thenReturn(mockOptions);
        when(mockContext.getRequest()).thenReturn(mockRequest);

        QuizService quizService = new QuizService(null);
        quizService.setQuestionAttributes(mockContext, mockQuestion);

        verify(mockRequest).setAttribute("question", mockQuestion);
        verify(mockRequest).setAttribute("options", Arrays.stream(mockOptions).toList());
    }

    @Test
    void retrieveQuestion_withQuestionIdInSession_returnsQuestion() {
        Integer exampleQuestionId = 5;
        when(mockQuestionRepository.retrieveQuestion(exampleQuestionId)).thenReturn(Optional.of(mockQuestion));
        when(mockContext.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute(QuizService.CURRENT_QUESTION_ID_ATTRIBUTE)).thenReturn(exampleQuestionId);

        Optional<Question> result = mockQuizService.retrieveQuestion(mockContext);

        Assertions.assertTrue(result.isPresent());
        assertEquals(mockQuestion, result.get());
    }

    @Test
    void retrieveQuestion_withoutQuestionIdInSession_returnsEmpty() {
        when(mockContext.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute(QuizService.CURRENT_QUESTION_ID_ATTRIBUTE)).thenReturn(null);

        Optional<Question> result = mockQuizService.retrieveQuestion(mockContext);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void handleAnswerCorrect() throws Exception {
        RequestHandlerContext context = new RequestHandlerContext(mockRequest, mockResponse, mockSession);

        when(mockSession.getAttribute(anyString())).thenReturn(0);
        when(mockRequest.getRequestDispatcher(anyString())).thenReturn(mockDispatcher);

        mockQuizService.handleAnswer(true, context);

        verify(mockSession, times(1)).getAttribute(QuizService.CORRECT_ANSWERS_COUNT_ATTRIBUTE);
        verify(mockSession, times(1)).setAttribute(eq(QuizService.CORRECT_ANSWERS_COUNT_ATTRIBUTE), anyInt());
        verify(mockSession, times(1)).getAttribute(QuizService.CURRENT_QUESTION_ID_ATTRIBUTE);
    }

    @Test
    void testHandleAnswerCorrectAnswerAndNextQuestionPresent() throws Exception {
        when(mockSession.getAttribute(anyString())).thenReturn(0);
        when(mockRequest.getRequestDispatcher(anyString())).thenReturn(mockDispatcher);
        int questionId = 1;
        RequestHandlerContext mockContext = spy(new RequestHandlerContext(mockRequest, mockResponse, mockSession));
        mockContext.setQuestionId(questionId);
        Question nextQuestion = new Question(2, "questionText", new Option[] {}, "answer");

        when(mockQuestionRepository.findNextQuestion(questionId)).thenReturn(Optional.of(nextQuestion));

        mockQuizService.handleAnswer(true, mockContext);

        verify(mockSession, times(1)).getAttribute(QuizService.CORRECT_ANSWERS_COUNT_ATTRIBUTE);
        verify(mockSession, times(1)).setAttribute(eq(QuizService.CORRECT_ANSWERS_COUNT_ATTRIBUTE), anyInt());
        verify(mockSession, times(1)).setAttribute(eq(QuizService.CURRENT_QUESTION_ID_ATTRIBUTE), eq(nextQuestion.getId()));
    }

    @Test
    void handleAnswerIncorrect() throws Exception {
        RequestHandlerContext context = new RequestHandlerContext(mockRequest, mockResponse, mockSession);

        when(mockRequest.getRequestDispatcher(anyString())).thenReturn(mockDispatcher);

        QuizService quizService = spy(new QuizService(null));
        quizService.handleAnswer(false, context);

        verify(mockSession, times(1)).removeAttribute(QuizService.CURRENT_QUESTION_ID_ATTRIBUTE);
        verify(mockRequest, times(1)).getRequestDispatcher(QuizService.FAIL_PAGE_JSP);
    }

    @Test
    void determineNextPage_whenQuestionIsPresent_thenQuizPageJspReturned() {
        String actual = mockQuizService.determineNextPage(Optional.of(mockQuestion));

        assertEquals(QuizService.QUIZ_PAGE_JSP, actual);
    }

    @Test
    void determineNextPage_whenQuestionIsNotPresent_thenQuizFinishedJspReturned() {
        String actual = mockQuizService.determineNextPage(Optional.empty());

        assertEquals(QuizService.QUIZ_FINISHED_JSP, actual);
    }
}
