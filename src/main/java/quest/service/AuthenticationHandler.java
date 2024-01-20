package quest.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import quest.repository.UserRepository;

import java.io.IOException;

@Log4j2
@AllArgsConstructor
public class AuthenticationHandler {

    private final UserRepository userRepository;
    private final SecurityService securityService;

    public void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        String storedSalt = userRepository.getSaltByUsername(username);

        if (isValidLogin(username, password, storedSalt)) {
            log.info("User {} logged in successfully.", username);
            resp.sendRedirect("welcomePage.jsp");
        } else {
            handleFailedLogin(username, req, resp);
        }
    }

    public void handleRegistration(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("registerUsername");
        String password = req.getParameter("registerPassword");

        String salt = securityService.generateSalt();
        String hashedPassword = securityService.hashPassword(password, salt);

        userRepository.registerAndWriteUserToJson(username, hashedPassword, salt);
        log.info("User {} registered successfully.", username);
        resp.sendRedirect("index.jsp");
    }

    private boolean isValidLogin(String username, String password, String storedSalt) {
        if (storedSalt != null) {
            String hashedPassword = securityService.hashPassword(password, storedSalt);
            return userRepository.checkUser(username, hashedPassword);
        }
        return false;
    }

    private void handleFailedLogin(String username, HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        log.error("Invalid username or password: {}", username);
        req.setAttribute("errorMessage", "Invalid username or password");
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
