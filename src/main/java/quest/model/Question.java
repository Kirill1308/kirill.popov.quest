package quest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Question {
    private final String id;
    private final String text;
    private final String option1;
    private final String option2;
    private final String answer;
}
