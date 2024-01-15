package quest.repository;

public interface UserRepository {
    boolean checkUser(String username, String password);
}
