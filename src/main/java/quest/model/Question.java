package quest.model;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public final class Question {
    private final int id;
    private final String text;
    private final Option[] options;
    private final String answer;
}
