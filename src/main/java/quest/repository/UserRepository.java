package quest.repository;

public interface UserRepository {
    boolean authenticateUser(String username, String password);

    void registerAndWriteUserToJson(String username, String password, String salt);

    String getSaltByUsername(String username);
}
