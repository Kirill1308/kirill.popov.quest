package quest.service;

import lombok.AllArgsConstructor;
import quest.model.Question;

@AllArgsConstructor
public class QuizAnswerChecker implements AnswerChecker {
    private final Question question;

    @Override
    public boolean checkAnswer(String answer) {
        return question.getAnswer().equalsIgnoreCase(answer);
    }
}