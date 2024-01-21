package quest.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void constructorAndGetters() {
        String username = "john.doe";
        String password = "password123";
        String salt = "randomSalt";

        User user = new User(username, password, salt);

        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(salt, user.getSalt());
    }

    @Test
    void setter() {
        User user = new User("john.doe", "password123", "oldSalt");
        String newSalt = "newSalt";

        user.setSalt(newSalt);

        assertEquals(newSalt, user.getSalt());
    }

}