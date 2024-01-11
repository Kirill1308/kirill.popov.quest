package service;

import lombok.AllArgsConstructor;
import model.Question;
import model.QuestionType;
import repository.QuestionRepository;

import java.util.List;

@AllArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public Question findFirstQuestion() {
        List<Question> byQuestionType =
                questionRepository.findByQuestionType(QuestionType.GAME_START_QUESTION);

        if (byQuestionType.isEmpty()) {
            throw new RuntimeException("Not question to game start");
        }

        if (byQuestionType.size() > 1) {
            throw new RuntimeException("");
        }

        return byQuestionType.get(0);
    }
}
