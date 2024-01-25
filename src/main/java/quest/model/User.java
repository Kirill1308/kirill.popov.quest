package quest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class User {
    private final String username;
    private final String password;
    @Setter
    private String salt;
}
