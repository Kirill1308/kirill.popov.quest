package quest.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import quest.context.ApplicationContext;
import quest.repository.UserRepository;

import java.io.IOException;

@Log4j2
@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private static final UserRepository userRepository = ApplicationContext.getInstanceOf(UserRepository.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        log.info("Handling POST request for UserServlet");

        String action = req.getParameter("action");

        if ("login".equals(action)) {
            handleLogin(req, resp);
        } else if ("register".equals(action)) {
            handleRegistration(req, resp);
        } else {
            log.error("Invalid action: {}", action);
            resp.sendRedirect("error.html");
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        boolean isUserExist = userRepository.checkUser(username, password);
        if (isUserExist) {
            log.info("User {} logged in successfully.", username);
            resp.sendRedirect("welcomePage.jsp");
        } else {
            log.warn("Failed login attempt for user: {}", username);
            req.setAttribute("errorMessage", "Invalid username or password");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }

    private void handleRegistration(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("registerUsername");
        String password = req.getParameter("registerPassword");

        boolean isUserExist = userRepository.checkUser(username, password);
        if (!isUserExist) {
            userRepository.registerAndWriteUserToJson(username, password);
            log.info("User {} registered successfully.", username);
            resp.sendRedirect("index.jsp");
            return;
        }

        log.warn("Registration failed for user {}: User already exists.", username);
        req.setAttribute("errorMessage", "User already exists");
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }
}
