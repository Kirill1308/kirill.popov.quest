package quest.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonFileIOExceptionTest {
    @Test
    void jsonFileIOException_Constructor() {
        JsonFileIOException exception = new JsonFileIOException("Test message", new Throwable());

        assertEquals("Test message", exception.getMessage());
    }
}
