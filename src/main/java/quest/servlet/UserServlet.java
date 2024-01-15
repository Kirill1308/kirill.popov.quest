package quest.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import quest.context.ApplicationContext;
import quest.repository.UserRepository;

import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private static final UserRepository userRepository = ApplicationContext.getInstanceOf(UserRepository.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        boolean isUserExist = userRepository.checkUser(username, password);
        if (isUserExist) {
            resp.sendRedirect("welcomePage.jsp");
        } else {
            resp.sendRedirect("/");
        }
    }
}
