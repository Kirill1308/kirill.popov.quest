package quest.command;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import quest.service.AuthenticationHandler;

import java.io.IOException;

@AllArgsConstructor
public class LoginCommand implements ActionCommand {
    private final AuthenticationHandler authenticationHandler;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        authenticationHandler.handleLogin(request, response);
    }
}
