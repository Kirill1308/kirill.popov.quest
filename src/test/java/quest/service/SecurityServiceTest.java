package quest.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SecurityServiceTest {
    private static final SecurityService securityService = new SecurityService();

    @Test
    void generateSalt_returnNonNullValue() {
        String salt = securityService.generateSalt();

        assertNotNull(salt);
    }

    @Test
    void generateSalt_returnDifferentValues() {
        String salt1 = securityService.generateSalt();
        String salt2 = securityService.generateSalt();

        assertNotEquals(salt1, salt2);
    }

    @Test
    void generateSalt_return32CharacterString() {
        String salt = securityService.generateSalt();

        assertEquals(32, salt.length());
    }

    @Test
    void hashPassword_returnCorrectHash() {
        String password = "testPassword";
        String salt = securityService.generateSalt();
        String hashedPassword = securityService.hashPassword(password, salt);

        assertEquals(64, hashedPassword.length());
    }

    @Test
    void hashPassword_returnDifferentHashesForDifferentSalts() {
        String password = "testPassword";
        String salt1 = securityService.generateSalt();
        String salt2 = securityService.generateSalt();

        assertNotEquals(securityService.hashPassword(password, salt1), securityService.hashPassword(password, salt2));
    }
}
