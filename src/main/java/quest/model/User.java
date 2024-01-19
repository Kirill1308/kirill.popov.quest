package quest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class User {
    private final String username;
    private final String password;
    @Setter
    private String salt;
}
