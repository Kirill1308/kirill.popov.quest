package quest.repository;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import quest.model.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JsonFileQuestionRepositoryTest {
    @Spy
    private JsonFileQuestionRepository spyRepo;

    @Test
    void getGson_shouldReturnGson() {
        JsonFileQuestionRepository questionRepository = new JsonFileQuestionRepository();

        Gson gson = questionRepository.getGson();

        assertNotNull(gson, "Gson instance should not be null");
        assertSame(gson, questionRepository.getGson(), "The same Gson instance should be returned");
    }

    @Test
    void retrieveQuestion_returnsNonNull() {
        Question question = new Question(1, "text", null, "answer");
        when(spyRepo.retrieveQuestion(1)).thenReturn(Optional.of(question));

        Optional<Question> retrievedQuestion = spyRepo.retrieveQuestion(1);
        assertTrue(retrievedQuestion.isPresent(), "Expected question for valid id");
    }

    @Test
    void retrieveQuestion_returnsEmpty() {
        Optional<Question> question = spyRepo.retrieveQuestion(100);
        assertTrue(question.isEmpty(), "Expected no question for invalid id");
    }

    @Test
    void getQuestionById_whenQuestionIdNotExist() {
        Question question1 = new Question(1, "text", null, "answer");
        Question question2 = new Question(2, "text", null, "answer");

        lenient().when(spyRepo.getQuestions()).thenReturn(Arrays.asList(question1, question2));

        Integer testId = 3;
        assertEquals(Optional.empty(), spyRepo.getQuestionById(testId));
    }

    @Test
    void getQuestionById_whenQuestionIdExist() {
        Question question1 = new Question(1, "text", null, "answer");
        Question question2 = new Question(2, "text", null, "answer");

        lenient().when(spyRepo.getQuestions()).thenReturn(Arrays.asList(question1, question2));
        lenient().when(spyRepo.getQuestionById(1)).thenReturn(Optional.of(question1));

        Integer testId = 1;
        assertEquals(Optional.of(question1), spyRepo.getQuestionById(testId));
    }

    @Test
    void getCorrectAnswerById_whenAnswerExists() {
        when(spyRepo.getCorrectAnswerById(1)).thenReturn(Optional.of("Answer"));

        Optional<String> answer = spyRepo.getCorrectAnswerById(1);

        assertEquals("Answer", answer.orElse(null));
    }

    @Test
    void findNextQuestion_withPresentId() {
        when(spyRepo.findNextQuestion(1)).thenReturn(Optional.of(new Question(2, "question2", null, "answer2")));

        Question findNextQuestionResult = spyRepo.findNextQuestion(1).orElse(null);

        assertNotNull(findNextQuestionResult);
        assertEquals(2, findNextQuestionResult.getId());
    }

    @Test
    void findNextQuestion_withNullId_returnsFirstQuestion() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question(1, "question1", null, "answer1"));
        questions.add(new Question(2, "question2", null, "answer2"));

        when(spyRepo.findNextQuestion(null)).thenReturn(Optional.of(questions.get(0)));

        assertEquals(questions.get(0), spyRepo.findNextQuestion(null).orElse(null));
    }

    @Test
    void findFirstQuestion_whenQuestionsExist() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question(1, "question1", null, "answer1"));
        questions.add(new Question(2, "question2", null, "answer2"));

        when(spyRepo.findNextQuestion(0)).thenReturn(Optional.of(questions.get(1)));

        assertEquals(questions.get(1), spyRepo.findNextQuestion(0).orElse(null));
    }

    @Test
    void findFirstQuestion_whenNoQuestionsExist() {
        ArrayList<Question> questions = new ArrayList<>();

        lenient().doReturn(questions).when(spyRepo).getQuestions();

        lenient().when(spyRepo.findFirstQuestion()).thenReturn(Optional.empty());
        Optional<Question> result = spyRepo.findFirstQuestion();

        assertFalse(result.isPresent(), "expected no result");
    }

    @Test
    void isCorrect_whenSubmittedAnswerIsCorrect_returnTrue() {
        Integer testQuestionId = 1;
        String correctAnswer = "Test Answer";

        when(spyRepo.getCorrectAnswerById(testQuestionId)).thenReturn(Optional.of(correctAnswer));

        assertTrue(spyRepo.checkAnswer(testQuestionId, correctAnswer), "Expected the method to return true for the correct answer");
    }

    @Test
    void isCorrect_returnFalse() {
        Integer testQuestionId = 1;
        String correctAnswer = "Test Answer";
        String incorrectAnswer = "Wrong Answer";

        when(spyRepo.getCorrectAnswerById(testQuestionId)).thenReturn(Optional.of(correctAnswer));

        assertFalse(spyRepo.checkAnswer(testQuestionId, incorrectAnswer), "Expected the method to return false for the incorrect answer");
    }
}
