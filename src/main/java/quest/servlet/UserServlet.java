package quest.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import quest.command.ActionCommand;
import quest.command.UserAction;
import quest.context.ApplicationContext;
import quest.exception.CommandNotFoundException;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Log4j2
@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private static final Map<UserAction, ActionCommand> actionCommands = ApplicationContext.getActionCommands();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String actionParam = request.getParameter("action");

        try {
            UserAction action = UserAction.valueOf(actionParam.toUpperCase(Locale.ENGLISH));
            ActionCommand command = Optional.ofNullable(actionCommands.get(action))
                    .orElseThrow(() -> new CommandNotFoundException("Command not found"));
            
            command.execute(request, response);
        } catch (IllegalArgumentException e) {
            throw new CommandNotFoundException("Command not found", e);
        }
    }
}
