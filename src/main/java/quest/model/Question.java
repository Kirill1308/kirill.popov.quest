package quest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class Question {
    private final int id;
    private final String text;
    private final String answer;
}