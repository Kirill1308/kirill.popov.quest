package quest.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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
    public static final String USERS_FULL_PATH_JSON = "C:\\Users\\kpopo\\IdeaProjects\\kirill.popov.quest\\src\\main\\resources\\users.json";
    private Gson gson = new Gson();
    private final List<User> users;

    public UserRepositoryImpl() {
        users = loadUsersFromJSON();
    }

    private List<User> loadUsersFromJSON() {
        try (Reader reader = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(USERS_JSON)))) {
            return gson.fromJson(reader, new TypeToken<List<User>>() {}.getType());
        } catch (Exception e) {
            log.error("Error reading users from JSON file.", e);
            throw new JsonFileIOException("Error reading users from JSON file.", e);
        }
    }

    @Override
    public boolean checkUser(String username, String password) {
        return users.stream()
                .anyMatch(user -> user.getUsername().equals(username) && user.getPassword().equals(password));
    }

    @Override
    public void registerAndWriteUserToJson(String username, String password) {
        users.add(new User(username, password));

        gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(users);

        try (FileWriter writer = new FileWriter(USERS_FULL_PATH_JSON)) {
            writer.write(json);
            writer.write(System.lineSeparator());
            log.info("User registered and JSON file updated: {}", USERS_FULL_PATH_JSON);
        } catch (IOException e) {
            log.error("Error writing file.", e);
            throw new JsonFileIOException("Error writing file.", e);
        }
    }
}
