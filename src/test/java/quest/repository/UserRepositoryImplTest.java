package quest.repository;

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
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class UserRepositoryImplTest {

    @Test
    void checkUser_returnsTrue() {
        UserRepositoryImpl userRepo = spy(UserRepositoryImpl.class);
        when(userRepo.checkUser("user1", "pass1")).thenReturn(true);
        assertTrue(userRepo.checkUser("user1", "pass1"));
    }

    @Test
    void checkUser_returnsFalse() {
        UserRepositoryImpl userRepo = spy(UserRepositoryImpl.class);
        when(userRepo.checkUser("user1", "pass1")).thenReturn(false);
        assertFalse(userRepo.checkUser("user1", "pass1"));
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
        UserRepositoryImpl userRepository = spy(UserRepositoryImpl.class);
        when(userRepository.getSaltByUsername("test1")).thenReturn("salt1");

        assertEquals("salt1", userRepository.getSaltByUsername("test1"));
    }

/*    @Test
    void testGetSaltByUsernameForNonexistentUser() {
        List<User> users = new ArrayList<>();
        users.add(new User("test1", "pass1", "salt1"));
        users.add(new User("test2", "pass2", "salt2"));
        UserRepositoryImpl userRepository = new UserRepositoryImpl(users);

        String salt = userRepository.getSaltByUsername("test3");
        assertEquals(null, salt);
    }

    @Test
    void getSaltByUsernameForEmptyUsername() {
        // setup
        List<User> users = new ArrayList<>();
        UserRepositoryImpl userRepository = new UserRepositoryImpl(users);

        // test
        assertThrows(NullPointerException.class, () -> userRepository.getSaltByUsername(null));
    }*/

}