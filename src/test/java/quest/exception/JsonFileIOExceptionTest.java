package quest.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonFileIOExceptionTest {
    @Test
    void jsonFileIOException() {
        String message = "Instance not found!";
        try {
            throw new JsonFileIOException(message);
        } catch (JsonFileIOException e) {
            assertEquals(e.getMessage(), message);
        }
    }

}
