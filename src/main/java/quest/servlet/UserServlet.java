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

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import static java.util.Objects.isNull;

@Log4j2
@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private static final Map<UserAction, ActionCommand> actionCommands = ApplicationContext.getActionCommands();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String actionParam = request.getParameter("action");
        try {
            UserAction action = UserAction.valueOf(actionParam.toUpperCase(Locale.ENGLISH));
            ActionCommand command = actionCommands.get(action);

            if (!isNull(command)) {
                command.execute(request, response);
            } else {
                response.sendRedirect("error.html");
            }
        } catch (IllegalArgumentException e) {
            response.sendRedirect("error.html");
        }
    }
}
