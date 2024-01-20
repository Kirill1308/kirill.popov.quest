package quest.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InstanceNotFoundExceptionTest {
    @Test
    void instanceNotFoundException() {
        String message = "Instance not found!";
        try {
            throw new InstanceNotFoundException(message);
        } catch (InstanceNotFoundException e) {
            assertEquals(e.getMessage(), message);
        }
    }
}
