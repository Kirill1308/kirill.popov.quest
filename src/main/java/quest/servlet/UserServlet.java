package quest.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import quest.context.ApplicationContext;
import quest.service.AuthenticationHandler;

import java.io.IOException;

@Log4j2
@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private static final AuthenticationHandler authenticationHandler = ApplicationContext.getInstanceOf(AuthenticationHandler.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        log.info("Handling POST request for UserServlet");

        String action = req.getParameter("action");

        if ("login".equals(action)) {
            authenticationHandler.handleLogin(req, resp);
        } else if ("register".equals(action)) {
            authenticationHandler.handleRegistration(req, resp);
        } else {
            log.error("Invalid action: {}", action);
            resp.sendRedirect("error.html");
        }
    }
}
