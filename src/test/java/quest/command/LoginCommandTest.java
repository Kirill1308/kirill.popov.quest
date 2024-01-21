package quest.command;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import quest.service.AuthenticationHandler;

import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoginCommandTest {

    @Mock
    private AuthenticationHandler mockAuthenticationHandler;
    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpServletResponse mockResponse;

    @Test
    void execute_runsHandleLoginOfAuthenticationHandler() throws ServletException, IOException {
        LoginCommand loginCommand = new LoginCommand(mockAuthenticationHandler);

        loginCommand.execute(mockRequest, mockResponse);

        verify(mockAuthenticationHandler, times(1)).handleLogin(mockRequest, mockResponse);
    }
}