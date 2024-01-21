package quest.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {
    @Test
    void testConstructorAndGetters() {
        Integer id = 1;
        String text = "What is the capital of France?";
        Option[] options = new Option[]{new Option("A")};
        String answer = "A";

        Question question = new Question(id, text, options, answer);

        assertEquals(id, question.getId());
        assertEquals(text, question.getText());
        assertArrayEquals(options, question.getOptions());
        assertEquals(answer, question.getAnswer());
    }

}