package io;

import com.google.gson.Gson;
import model.Question;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class JSONQuestionReader {
    private final Gson gson = new Gson();

    public Question readQuestionFromJSON(String filePath) {
        Question question;
        String id;
        String questionText;
        String option1;
        String option2;
        String answer;

        try {
            FileReader fileReader = new FileReader(filePath);
            question = gson.fromJson(fileReader, Question.class);
            id = question.getId();
            questionText = question.getQuestion();
            option1 = question.getOption1();
            option2 = question.getOption2();
            answer = question.getAnswer();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return new Question(id, questionText, option1, option2, answer);
    }
}
