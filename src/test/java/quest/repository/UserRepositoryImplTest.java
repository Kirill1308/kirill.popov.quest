package quest.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import quest.exception.JsonFileIOException;
import quest.model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
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
    void testRegisterAndWriteUserToJson() {
        String username = "user";
        String password = "password";
        String salt = "salt";
        userRepository.registerAndWriteUserToJson(username, password, salt);

        StringBuilder jsonContent = readJsonFileContent();

        assertTrue(jsonContent.toString().contains(username));
        assertTrue(jsonContent.toString().contains(password));
        assertTrue(jsonContent.toString().contains(salt));
    }

    private StringBuilder readJsonFileContent() {
        File jsonFile = new File("src/main/resources/users.json");
        StringBuilder jsonContent = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(jsonFile))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                jsonContent.append(sCurrentLine);
            }

        } catch (IOException e) {
            throw new JsonFileIOException("Error reading file.");
        }
        return jsonContent;
    }

    @Test
    void checkUser_with_valid_credentials_should_return_true() {
        when(userRepository.authenticateUser(validUsername, validPassword)).thenReturn(true);

        boolean result = userRepository.authenticateUser(validUsername, validPassword);

        assertTrue(result, "checkUser should return true when valid username and password are provided.");
    }

    @Test
    void checkUser_with_invalid_username_should_return_false() {
        String invalidUsername = "invalidUser";

        when(userRepository.authenticateUser(invalidUsername, validPassword)).thenReturn(false);

        boolean result = userRepository.authenticateUser(invalidUsername, validPassword);
        assertFalse(result, "checkUser should return false when invalid username is provided.");
    }

    @Test
    void checkUser_with_invalid_password_should_return_false() {
        String invalidPassword = "invalidPassword";

        when(userRepository.authenticateUser(validUsername, invalidPassword)).thenReturn(false);

        boolean result = userRepository.authenticateUser(validUsername, invalidPassword);
        assertFalse(result, "checkUser should return false when invalid password is provided.");
    }

    @Test
    void testAuthenticateUser_ValidCredentials() {
        assertTrue(userRepository.authenticateUser("user1", "password1"));
    }

    @Test
    void testAuthenticateUser_InvalidUsername() {
        assertFalse(userRepository.authenticateUser("invalidUser", "password1"));
    }

    @Test
    void testAuthenticateUser_InvalidPassword() {
        assertFalse(userRepository.authenticateUser("user1", "invalidPassword"));
    }

    @Test
    void testAuthenticateUser_NullCredentials() {
        assertFalse(userRepository.authenticateUser(null, null));
    }

    @Test
    void testIsUsernameTaken() {
        assertTrue(userRepository.isUsernameTaken("user1"));
        assertTrue(userRepository.isUsernameTaken("user2"));
        assertFalse(userRepository.isUsernameTaken("user3"));
    }

    @Test
    void testIsUsernameTaken_NullUsername() {
        assertFalse(userRepository.isUsernameTaken(null));
    }

    @Test
    void getSaltByUsername_returnsSalt() {
        assertEquals("salt1", userRepository.getSaltByUsername("user1"));
    }

    @Test
    void testGetSaltByUsername_NullUsername() {
        assertNull(userRepository.getSaltByUsername(null));
    }
}
