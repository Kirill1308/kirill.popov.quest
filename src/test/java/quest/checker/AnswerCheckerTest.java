package quest.checker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import quest.repository.QuestionRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AnswerCheckerTest {
    private QuestionRepository mockQuestionRepository;
    private AnswerChecker answerChecker;

    @BeforeEach
    void setUp() {
        mockQuestionRepository = mock(QuestionRepository.class);
        answerChecker = new AnswerChecker(mockQuestionRepository);
    }

    @Test
    void isCorrect_returnsTrue() {
        Integer testQuestionId = 1;
        String correctAnswer = "Test Answer";

        when(mockQuestionRepository.getCorrectAnswerById(testQuestionId)).thenReturn(Optional.of(correctAnswer));

        assertTrue(answerChecker.isCorrect(testQuestionId, correctAnswer), "Expected the method to return true for the correct answer");
    }

    @Test
    void isCorrect_returnsFalse() {
        Integer testQuestionId = 1;
        String correctAnswer = "Test Answer";
        String incorrectAnswer = "Wrong Answer";

        when(mockQuestionRepository.getCorrectAnswerById(testQuestionId)).thenReturn(Optional.of(correctAnswer));

        assertFalse(answerChecker.isCorrect(testQuestionId, incorrectAnswer), "Expected the method to return false for the incorrect answer");
    }
}