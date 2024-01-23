package quest.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import quest.exception.PasswordHashingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

class SecurityServiceTest {

    @Test
    void generateSalt_returnsNonNullValue() {
        SecurityService securityService = new SecurityService();

        String salt = securityService.generateSalt();
        Assertions.assertNotNull(salt);
    }

    @Test
    void generateSalt_returnsDifferentValues() {
        SecurityService securityService = new SecurityService();

        String salt1 = securityService.generateSalt();
        String salt2 = securityService.generateSalt();
        Assertions.assertNotEquals(salt1, salt2);
    }

    @Test
    void generateSalt_returns32CharacterString() {
        SecurityService securityService = new SecurityService();

        String salt = securityService.generateSalt();
        Assertions.assertEquals(32, salt.length());
    }

    @Test
    void testHashPasswordReturnsCorrectHash() {
        SecurityService securityService = new SecurityService();
        String password = "testPassword";
        String salt = securityService.generateSalt();
        String hashedPassword = securityService.hashPassword(password, salt);

        assertEquals(64, hashedPassword.length());
    }

    @Test
    void testHashPasswordReturnsDifferentHashesForDifferentSalts() {
        SecurityService securityService = new SecurityService();
        String password = "testPassword";
        String salt1 = securityService.generateSalt();
        String salt2 = securityService.generateSalt();

        assertNotEquals(securityService.hashPassword(password, salt1), securityService.hashPassword(password, salt2));
    }
}
