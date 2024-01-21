package quest.command;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import quest.service.AuthenticationHandler;

import java.io.IOException;

@AllArgsConstructor
public class RegisterCommand implements ActionCommand {
    private final AuthenticationHandler authenticationHandler;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        authenticationHandler.handleRegistration(req, resp);
    }
}
