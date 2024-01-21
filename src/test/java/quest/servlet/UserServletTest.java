package quest.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServletTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Spy
    UserServlet userServlet;

    @Test
    void doPost_whenActionCommandIsNull_shouldRedirect() throws IOException, ServletException {
        when(request.getParameter("action")).thenReturn("invalidAction");

        userServlet.doPost(request, response);

        verify(response, times(1)).sendRedirect("error.html");
    }
}
