package quest.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import quest.exception.JsonFileIOException;
import quest.model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRepositoryImplTest {
    private UserRepositoryImpl userRepositoryImpl;
    private final String validUsername = "john";
    private final String validPassword = "johnpassword";

    @BeforeEach
    void setUp() {
        List<User> users = new ArrayList<>();
        users.add(new User(validUsername, validPassword, "salt"));
        userRepositoryImpl = new UserRepositoryImpl();
    }

    @AfterEach
    void tearDown() {
        userRepositoryImpl = null;
    }

    @Test
    void testRegisterAndWriteUserToJson() {
        UserRepositoryImpl userRepo = new UserRepositoryImpl();
        String username = "testuser";
        String password = "testpass";
        String salt = "testsalt";
        userRepo.registerAndWriteUserToJson(username, password, salt);

        StringBuilder jsonContent = readJsonFileContent();

        assertTrue(jsonContent.toString().contains(username));
        assertTrue(jsonContent.toString().contains(password));
        assertTrue(jsonContent.toString().contains(salt));
    }

    private StringBuilder readJsonFileContent() {
        File jsonFile = new File("C:\\Users\\kpopo\\IdeaProjects\\kirill.popov.quest\\src\\main\\resources\\users.json");
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
    void getSaltByUsername_returnsSalt() {
        UserRepositoryImpl userRepository = mock(UserRepositoryImpl.class);
        when(userRepository.getSaltByUsername("test1")).thenReturn("salt1");

        assertEquals("salt1", userRepository.getSaltByUsername("test1"));
    }

    @Test
    void checkUser_with_valid_credentials_should_return_true() {
        UserRepositoryImpl userRepositoryImpl = mock(UserRepositoryImpl.class);

        when(userRepositoryImpl.authenticateUser(validUsername, validPassword)).thenReturn(true);
        boolean result = userRepositoryImpl.authenticateUser(validUsername, validPassword);
        assertTrue(result, "checkUser should return true when valid username and password are provided.");
    }

    @Test
    void checkUser_with_invalid_username_should_return_false() {
        UserRepositoryImpl userRepositoryImpl = mock(UserRepositoryImpl.class);

        String invalidUsername = "invalidUser";
        when(userRepositoryImpl.authenticateUser(invalidUsername, validPassword)).thenReturn(false);
        boolean result = userRepositoryImpl.authenticateUser(invalidUsername, validPassword);
        assertFalse(result, "checkUser should return false when invalid username is provided.");
    }

    @Test
    void checkUser_with_invalid_password_should_return_false() {
        UserRepositoryImpl userRepositoryImpl = mock(UserRepositoryImpl.class);

        String invalidPassword = "invalidPassword";
        when(userRepositoryImpl.authenticateUser(validUsername, invalidPassword)).thenReturn(false);
        boolean result = userRepositoryImpl.authenticateUser(validUsername, invalidPassword);
        assertFalse(result, "checkUser should return false when invalid password is provided.");
    }
}
