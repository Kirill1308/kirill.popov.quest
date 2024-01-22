package quest.context;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestHandlerContextTest {

    private static final String CURRENT_QUESTION_ID_ATTRIBUTE = "currentQuestionId";

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

    @Mock
    private HttpSession mockSession;

    @Test
    void requestHandlerContext_ConstructorAndGetters() {
        when(mockSession.getAttribute(CURRENT_QUESTION_ID_ATTRIBUTE)).thenReturn(42);

        RequestHandlerContext context = new RequestHandlerContext(mockRequest, mockResponse, mockSession);

        assertEquals(mockRequest, context.getRequest());
        assertEquals(mockResponse, context.getResponse());
        assertEquals(mockSession, context.getSession());
        assertEquals(42, context.getQuestionId());
    }

    @Test
    void constructor_WithNullQuestionIdAttribute() {
        when(mockSession.getAttribute(CURRENT_QUESTION_ID_ATTRIBUTE)).thenReturn(null);

        RequestHandlerContext context = new RequestHandlerContext(mockRequest, mockResponse, mockSession);

        assertNull(context.getQuestionId());
    }
}
