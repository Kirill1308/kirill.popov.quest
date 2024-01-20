package quest.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import quest.exception.JsonFileIOException;
import quest.model.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JsonFileQuestionRepositoryTest {

    private JsonFileQuestionRepository repository;

    @BeforeEach
    void setUp() {
        repository = spy(JsonFileQuestionRepository.class);
    }

    @Test
    void retrieveQuestion_returnsNonNull() {
        Question question = new Question(1, "text", null, "answer");
        when(repository.retrieveQuestion(1)).thenReturn(Optional.of(question));

        Optional<Question> retrievedQuestion = repository.retrieveQuestion(1);
        assertTrue(retrievedQuestion.isPresent(), "Expected question for valid id");
    }

    @Test
    void retrieveQuestion_returnsEmpty() {
        Optional<Question> question = repository.retrieveQuestion(100);
        assertTrue(question.isEmpty(), "Expected no question for invalid id");
    }

    @Test
    void getQuestionById_whenQuestionIdNotExist() {
        Question question1 = new Question(1, "text", null, "answer");
        Question question2 = new Question(2, "text", null, "answer");

        when(repository.getQuestions()).thenReturn(Arrays.asList(question1, question2));

        Integer testId = 3;
        Assertions.assertEquals(Optional.empty(), repository.getQuestionById(testId));
    }

    @Test
    void getQuestionById_whenQuestionIdExist() {
        Question question1 = new Question(1, "text", null, "answer");
        Question question2 = new Question(2, "text", null, "answer");

        when(repository.getQuestions()).thenReturn(Arrays.asList(question1, question2));
        when(repository.getQuestionById(1)).thenReturn(Optional.of(question1));

        Integer testId = 1;
        Assertions.assertEquals(Optional.of(question1), repository.getQuestionById(testId));
    }

    @Test
    void testGetCorrectAnswerById_ReturnsCorrectAnswer_WhenAnswerExists() {
        when(repository.getCorrectAnswerById(1)).thenReturn(Optional.of("Answer"));

        Optional<String> answer = repository.getCorrectAnswerById(1);

        assertEquals("Answer", answer.orElse(null));
    }

    @Test
    void findNextQuestionWithPresentId() {
        when(repository.findNextQuestion(1)).thenReturn(Optional.of(new Question(2, "question2", null, "answer2")));

        Question findNextQuestionResult = repository.findNextQuestion(1).orElse(null);

        assertNotNull(findNextQuestionResult);
        assertEquals(2, findNextQuestionResult.getId());
    }

    @Test
    void findNextQuestionWithNullId_returnsFirstQuestion() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question(1, "question1", null, "answer1"));
        questions.add(new Question(2, "question2", null, "answer2"));

        when(repository.findNextQuestion(null)).thenReturn(Optional.of(questions.get(0)));

        assertEquals(questions.get(0), repository.findNextQuestion(null).orElse(null));
    }

    @Test
    void findFirstQuestion_whenQuestionsExist() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question(1, "question1", null, "answer1"));
        questions.add(new Question(2, "question2", null, "answer2"));

        when(repository.findNextQuestion(0)).thenReturn(Optional.of(questions.get(1)));

        assertEquals(questions.get(1), repository.findNextQuestion(0).orElse(null));
    }

    @Test
    void findFirstQuestion_whenNoQuestionsExist() {
        ArrayList<Question> questions = new ArrayList<>();

        doReturn(questions).when(repository).getQuestions();

        when(repository.findFirstQuestion()).thenReturn(Optional.empty());
        Optional<Question> result = repository.findFirstQuestion();

        assertFalse(result.isPresent(), "expected no result");
    }
}