package quest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class Question {
    private final Integer id;
    private final String text;
    private final Option[] options;
    private final String answer;
}
