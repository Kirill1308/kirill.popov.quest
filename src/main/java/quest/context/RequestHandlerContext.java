package quest.context;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;

import static quest.service.QuizService.CURRENT_QUESTION_ID_ATTRIBUTE;

@Getter
public final class RequestHandlerContext {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final HttpSession session;
    @Setter
    private Integer questionId;

    public RequestHandlerContext(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
        setQuestionId();
    }
    public void setQuestionId() {
        this.questionId = (Integer) session.getAttribute(CURRENT_QUESTION_ID_ATTRIBUTE);
    }
}
