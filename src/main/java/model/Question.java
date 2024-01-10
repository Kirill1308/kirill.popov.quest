package model;

import lombok.Getter;

@Getter
public class Question {
    private final String id;
    private final String question;
    private final String option1;
    private final String option2;
    private final String answer;

    public Question(String id, String question, String option1, String option2, String answer) {
        this.id = id;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", question='" + question + '\'' +
                ", option1='" + option1 + '\'' +
                ", option2='" + option2 + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
