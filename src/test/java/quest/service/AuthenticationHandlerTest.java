package quest.service;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import quest.repository.UserRepository;
import quest.repository.UserRepositoryImpl;

import java.io.IOException;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthenticationHandlerTest {
    private final UserRepositoryImpl userRepository = mock(UserRepositoryImpl.class);
    private final SecurityService securityService = mock(SecurityService.class);
    private final AuthenticationHandler authenticationHandler = new AuthenticationHandler(userRepository, securityService);
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

    @Test
    void handleLogin_success() throws IOException, ServletException {

        when(request.getParameter("username")).thenReturn("user");
        when(request.getParameter("password")).thenReturn("password");
        when(userRepository.getSaltByUsername(anyString())).thenReturn("salt");
        when(securityService.hashPassword(anyString(), anyString())).thenReturn("hashedPassword");
        when(userRepository.authenticateUser(anyString(), anyString())).thenReturn(true);

        authenticationHandler.handleLogin(request, response);

        verify(response).sendRedirect("homeWelcome.jsp");
    }

    @Test
    void handleLogin_failure() throws IOException, ServletException {

        when(request.getParameter("username")).thenReturn("user");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getRequestDispatcher("index.jsp")).thenReturn(dispatcher);
        when(userRepository.getSaltByUsername(anyString())).thenReturn("salt");
        when(securityService.hashPassword(anyString(), anyString())).thenReturn("hashedPassword");
        when(userRepository.authenticateUser(anyString(), anyString())).thenReturn(false);

        authenticationHandler.handleLogin(request, response);

        verify(request).setAttribute("errorMessage", "Invalid username or password");
        verify(request).getRequestDispatcher("index.jsp");
        verify(dispatcher).forward(request, response);
    }

    @Test
    void handleRegistration() throws IOException, ServletException {

        when(request.getParameter("registerUsername")).thenReturn("user");
        when(request.getParameter("registerPassword")).thenReturn("password");
        when(securityService.generateSalt()).thenReturn("salt");
        when(securityService.hashPassword(anyString(), anyString())).thenReturn("hashedPassword");

        authenticationHandler.handleRegistration(request, response);

        verify(userRepository).registerAndWriteUserToJson(anyString(), anyString(), anyString());
        verify(response).sendRedirect("index.jsp");
    }
}
