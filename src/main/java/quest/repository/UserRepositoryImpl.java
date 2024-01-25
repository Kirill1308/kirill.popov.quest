package quest.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import quest.exception.JsonFileIOException;
import quest.model.User;


import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Objects;

@Log4j2
public class UserRepositoryImpl implements UserRepository {
    public static final String USERS_JSON = "/users.json";
    @Getter
    private final List<User> users;
    private Gson gson = new Gson();

    public UserRepositoryImpl() {
        users = loadUsersFromJSON();
    }

    private List<User> loadUsersFromJSON() {
        try (Reader reader = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(USERS_JSON)))) {
            return gson.fromJson(reader, new TypeToken<List<User>>() {
            }.getType());
        } catch (Exception e) {
            log.error("Error reading users from JSON file.", e);
            throw new JsonFileIOException("Error reading users from JSON file.", e);
        }
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        return users.stream()
                .anyMatch(user -> user.getUsername().equals(username) && user.getPassword().equals(password));
    }

    public boolean isUsernameTaken(String username) {
        return users.stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }

    @Override
    public void registerAndWriteUserToJson(String username, String password, String salt) {
        if (username == null || password == null || salt == null) {
            throw new JsonFileIOException("Input(s) cannot be null", new NullPointerException().getCause());
        }

        users.add(new User(username, password, salt));

        gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(users);

        String path = Objects.requireNonNull(getClass().getClassLoader().getResource("users.json")).getFile();
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(json);
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            log.error("Error writing file.", e);
            throw new JsonFileIOException("Error writing file.", e);
        }
    }

    @Override
    public String getSaltByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .map(User::getSalt)
                .findFirst()
                .orElse(null);
    }
}
