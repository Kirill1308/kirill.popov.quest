package quest.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j2;
import quest.exception.JsonFileIOException;
import quest.model.User;

import java.io.*;
import java.util.List;
import java.util.Objects;

@Log4j2
public class UserRepositoryImpl implements UserRepository {
    public static final String USERS_JSON = "/users.json";
    public static final String USERS_FULL_PATH_JSON = "C:\\Users\\kpopo\\IdeaProjects\\kirill.popov.quest\\src\\main\\resources\\users.json";
    private Gson gson = new Gson();
    private final List<User> users;

    public UserRepositoryImpl() {
        users = loadUsersFromJSON();
    }

    private List<User> loadUsersFromJSON() {
        try (Reader reader = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(USERS_JSON)))) {
            return gson.fromJson(reader, new TypeToken<List<User>>() {
            }.getType());
        } catch (Exception e) {
            log.error("Error reading users from JSON file.", e);
            throw new JsonFileIOException("Error reading users from JSON file.");
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
        users.add(new User(username, password, salt));

        gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(users);

        String file = new File("src/main/resources/users.json").getAbsolutePath();
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(json);
            writer.write(System.lineSeparator());
            log.info("User registered and JSON file updated: {}", USERS_FULL_PATH_JSON);
            log.info("User stored in : {}", file);
        } catch (IOException e) {
            log.error("Error writing file.", e);
            throw new JsonFileIOException("Error writing file.");
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
