package quest.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InstanceNotFoundExceptionTest {
    @Test
    void instanceNotFoundException_constructor() {
        PasswordHashingException exception = new PasswordHashingException("Test message");

        assertEquals("Test message", exception.getMessage());
    }
}
