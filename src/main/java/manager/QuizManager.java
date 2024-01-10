package manager;

import model.Question;

import java.util.List;

public class QuizManager {
    private final List<Question> questions;

    public QuizManager(List<Question> questions) {
        this.questions = questions;
    }

    public Question getQuestion(int currentQuestionIndex) {
        if (currentQuestionIndex >= 0 && currentQuestionIndex < questions.size()) {
            return questions.get(currentQuestionIndex);
        } else {
            return null; // or throw an exception
        }
    }

    public boolean checkAnswer(Question question, String answer) {
        return question.getAnswer().equals(answer);
    }
}
