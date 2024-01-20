package quest.context;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RequestHandlerContextTest {

    // Testing the RequestHandlerContext Class.
    @Test
    void testRequestHandlerContext() {
        // Mock HttpServletRequest, HttpServletResponse and HttpSession
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        // Define the behaviour of these mocks.
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("CURRENT_QUESTION_ID")).thenReturn(20);

        // Create instance of class to test
        RequestHandlerContext context = mock(RequestHandlerContext.class);

        // Define the behavior of context mock
        when(context.getRequest()).thenReturn(request);
        when(context.getResponse()).thenReturn(response);
        when(context.getSession()).thenReturn(session);
        when(context.getQuestionId()).thenReturn(20);

        // Run the tests
        assertNotNull(context.getRequest());
        assertNotNull(context.getResponse());
        assertNotNull(context.getSession());
        assertEquals(Integer.valueOf(20), context.getQuestionId());
    }
}