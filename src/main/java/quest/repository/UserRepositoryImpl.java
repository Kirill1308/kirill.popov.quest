package quest.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import quest.exception.JsonFileIOException;
import quest.exception.UserNotFoundException;
import quest.model.User;

import java.io.*;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Log4j2
public class UserRepositoryImpl implements UserRepository {
    public static final String USERS_JSON = "/users.json";
    public static final String USERS_FULL_PATH_JSON = "C:\\Users\\kpopo\\IdeaProjects\\kirill.popov.quest\\src\\main\\resources\\users.json";
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

/*    @Override
    public void registerAndWriteUserToJson(String username, String password, String salt) {
        users.add(new User(username, password, salt));

        gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(users);

        URL url = getClass().getClassLoader().getResource(".");
        String pathToClasses = Objects.requireNonNull(url).getPath();
        Path path = Paths.get(pathToClasses, "../../../src/main/resources/users.json").toAbsolutePath().normalize();
        log.info("Path to users.json: {}", path);

        try (FileWriter writer = new FileWriter(path.toFile())) {
            writer.write(json);
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            log.error("Error writing file.", e);
            throw new JsonFileIOException("Error writing file.", e);
        }
    }*/

    @Override
    public void registerAndWriteUserToJson(String username, String password, String salt) {
        users.add(new User(username, password, salt));

        gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(users);

        // Assuming USERS_FULL_PATH_JSON is an absolute path
        Path fullPath = Paths.get(USERS_FULL_PATH_JSON);

        // Determine the base path (in this example, the project root)
        Path basePath = Paths.get("").toAbsolutePath().normalize();

        // Calculate the relative path
        Path relativePath = basePath.relativize(fullPath);

        log.info("Relative path to users.json: {}", relativePath);

        try (FileWriter writer = new FileWriter(relativePath.toFile())) {
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
