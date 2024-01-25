package quest.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PasswordHashingExceptionTest {
    @Test
    void passwordHashingException_constructor() {
        PasswordHashingException exception = new PasswordHashingException("Test message");

        assertEquals("Test message", exception.getMessage());
    }
}
