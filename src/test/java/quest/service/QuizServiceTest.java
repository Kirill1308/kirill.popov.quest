package quest.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.servlet.http.HttpSession;
import org.mockito.Mockito;
import quest.model.Option;
import quest.model.Question;
import quest.repository.QuestionRepository;
import quest.context.RequestHandlerContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class QuizServiceTest {
    private QuestionRepository mockQuestionRepository;
    private QuizService mockQuizService;
    private Question mockQuestion;
    private Option mockOption;
    private RequestHandlerContext mockContext;
    private HttpSession mockSession;
    private HttpServletRequest mockRequest;

    @BeforeEach
    void setUp() {
        mockQuestionRepository = mock(QuestionRepository.class);
        mockQuizService = new QuizService(mockQuestionRepository);
        mockQuestion = mock(Question.class);
        mockOption = mock(Option.class);
        mockContext = mock(RequestHandlerContext.class);
        mockSession = mock(HttpSession.class);
        mockRequest = mock(HttpServletRequest.class);

        when(mockContext.getSession()).thenReturn(mockSession);
        mockRequest = mock(HttpServletRequest.class);
        when(mockContext.getRequest()).thenReturn(mockRequest);
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

        QuizService quizService = new QuizService(null);
        quizService.setQuestionAttributes(mockRequest, mockQuestion);

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
        Assertions.assertEquals(mockQuestion, result.get());
    }

    @Test
    void retrieveQuestion_withoutQuestionIdInSession_returnsEmpty() {
        when(mockContext.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute(QuizService.CURRENT_QUESTION_ID_ATTRIBUTE)).thenReturn(null);

        Optional<Question> result = mockQuizService.retrieveQuestion(mockContext);

        Assertions.assertTrue(result.isEmpty());
    }
}
