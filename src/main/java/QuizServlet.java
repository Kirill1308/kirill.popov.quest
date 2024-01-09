import io.JSONQuestionReader;
import model.Question;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/QuizServlet")
public class QuizServlet extends HttpServlet {
    private final JSONQuestionReader jsonQuestionReader = new JSONQuestionReader();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String selectedChoice = request.getParameter("choice");

        if ("option1".equals(selectedChoice)) {
            Question question = jsonQuestionReader.readQuestionFromJSON("C:\\Users\\kpopo\\IdeaProjects\\kirill.popov.quest\\src\\main\\resources\\questions.json");
            request.setAttribute("question", question);

            RequestDispatcher dispatcher = request.getRequestDispatcher("QuizPage.html");
            dispatcher.forward(request, response);
        } else if ("option2".equals(selectedChoice)) {
            response.sendRedirect("Fail.html");
        }
    }
}
