package quest.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import quest.exception.JsonFileReadingException;
import quest.model.User;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Objects;

public class UserRepositoryImpl implements UserRepository {
    public static final String USERS_JSON = "/users.json";
    private final Gson gson = new Gson();
    private final List<User> users;

    public UserRepositoryImpl() {
        users = loadUsersFromJSON();
    }

    private List<User> loadUsersFromJSON() {
        try (Reader reader = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(USERS_JSON)))) {
            return gson.fromJson(reader, new TypeToken<List<User>>() {
            }.getType());
        } catch (Exception e) {
            throw new JsonFileReadingException("Error reading users from JSON file.", e);
        }
    }

    public boolean checkUser(String username, String password) {
        return users.stream()
                .anyMatch(user -> user.getUsername().equals(username) && user.getPassword().equals(password));
    }
}
