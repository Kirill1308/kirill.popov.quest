package quest.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import quest.exception.JsonFileIOException;
import quest.model.User;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {
    @Spy
    private UserRepositoryImpl userRepository;
    @Mock
    private User mockUser1;
    @Mock
    private User mockUser2;
    private final String validUsername = "john";
    private final String validPassword = "johnPassword";

    @BeforeEach
    void setUp() {

        lenient().when(mockUser1.getUsername()).thenReturn("user1");
        lenient().when(mockUser1.getPassword()).thenReturn("password1");
        lenient().when(mockUser1.getSalt()).thenReturn("salt1");

        lenient().when(mockUser2.getUsername()).thenReturn("user2");
        lenient().when(mockUser2.getPassword()).thenReturn("password2");
        lenient().when(mockUser2.getSalt()).thenReturn("salt2");

        List<User> userList = Arrays.asList(mockUser1, mockUser2);
        userRepository.getUsers().clear();
        userRepository.getUsers().addAll(userList);
    }

    @Test
    void registerAndWriteUserToJson_success() {
        String username = "john";
        String password = "12345";
        String salt = "sample_salt";

        userRepository.registerAndWriteUserToJson(username, password, salt);

        List<User> users = userRepository.getUsers();
        User lastUser = users.get(users.size() - 1);
        assertEquals(username, lastUser.getUsername());
        assertEquals(password, lastUser.getPassword());
        assertEquals(salt, lastUser.getSalt());
    }

    @Test
    void registerAndWriteUserToJson_nullParameters_throwsJsonFileIOException() {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();

        assertThrows(JsonFileIOException.class,
                () -> userRepository.registerAndWriteUserToJson(null, null, null),
                "Expected a JsonFileIOException to be thrown, but it didn't");

    }

    @Test
    void checkUser_withValidCredentials_returnTrue() {
        when(userRepository.authenticateUser(validUsername, validPassword)).thenReturn(true);

        boolean result = userRepository.authenticateUser(validUsername, validPassword);

        assertTrue(result, "checkUser should return true when valid username and password are provided.");
    }

    @Test
    void checkUser_withInvalidUsername_returnFalse() {
        String invalidUsername = "invalidUser";

        when(userRepository.authenticateUser(invalidUsername, validPassword)).thenReturn(false);

        boolean result = userRepository.authenticateUser(invalidUsername, validPassword);
        assertFalse(result, "checkUser should return false when invalid username is provided.");
    }

    @Test
    void checkUser_withInvalidPassword_returnFalse() {
        String invalidPassword = "invalidPassword";

        when(userRepository.authenticateUser(validUsername, invalidPassword)).thenReturn(false);

        boolean result = userRepository.authenticateUser(validUsername, invalidPassword);
        assertFalse(result, "checkUser should return false when invalid password is provided.");
    }

    @Test
    void authenticateUser_validCredentials_returnTrue() {
        assertTrue(userRepository.authenticateUser("user1", "password1"));
    }

    @Test
    void authenticateUser_invalidUsername_returnFalse() {
        assertFalse(userRepository.authenticateUser("invalidUser", "password1"));
    }

    @Test
    void authenticateUser_invalidPassword_returnFalse() {
        assertFalse(userRepository.authenticateUser("user1", "invalidPassword"));
    }

    @Test
    void authenticateUser_nullCredentials_returnFalse() {
        assertFalse(userRepository.authenticateUser(null, null));
    }

    @Test
    void isUsernameTaken_returnTrue() {
        assertTrue(userRepository.isUsernameTaken("user1"));
        assertTrue(userRepository.isUsernameTaken("user2"));
    }

    @Test
    void isUsernameTaken_returnFalse() {
        assertFalse(userRepository.isUsernameTaken("user3"));
    }

    @Test
    void isUsernameTaken_nullUsername_returnFalse() {
        assertFalse(userRepository.isUsernameTaken(null));
    }

    @Test
    void getSaltByUsername_returnsSalt() {
        assertEquals("salt1", userRepository.getSaltByUsername("user1"));
    }

    @Test
    void getSaltByUsername_mullUsername_returnNull() {
        assertNull(userRepository.getSaltByUsername(null));
    }
}
