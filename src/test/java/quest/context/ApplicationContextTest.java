package quest.context;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import quest.exception.InstanceNotFoundException;
import quest.repository.QuestionRepository;
import quest.repository.UserRepository;
import quest.repository.UserRepositoryImpl;
import quest.service.AuthenticationHandler;
import quest.service.QuizService;
import quest.service.SecurityService;

class ApplicationContextTest {
  
  @Test
  void getInstanceOfQuizService() {
      QuizService quizService = ApplicationContext.getInstanceOf(QuizService.class);
      Assertions.assertNotNull(quizService, "QuizService should not be null");
  }

  @Test
  void getInstanceOfQuestionRepository() {
      QuestionRepository questionRepository = ApplicationContext.getInstanceOf(QuestionRepository.class);
      Assertions.assertNotNull(questionRepository, "QuestionRepository should not be null");
  }

  @Test
  void getInstanceOfSecurityService() {
      SecurityService securityService = ApplicationContext.getInstanceOf(SecurityService.class);
      Assertions.assertNotNull(securityService, "SecurityService should not be null");
  }

  @Test
  void getInstanceOfUserRepository() {
      UserRepository userRepository = ApplicationContext.getInstanceOf(UserRepositoryImpl.class);
      Assertions.assertNotNull(userRepository, "UserRepository should not be null");
  }

  @Test
  void getInstanceOfAuthenticationHandler() {
      AuthenticationHandler authenticationHandler = ApplicationContext.getInstanceOf(AuthenticationHandler.class);
      Assertions.assertNotNull(authenticationHandler, "AuthenticationHandler should not be null");
  }

  @Test
  void getInstanceOfNonExistentClass() {
      Assertions.assertThrows(InstanceNotFoundException.class, () -> ApplicationContext.getInstanceOf(String.class), "Should Throw InstanceNotFoundException for non-existing class");
  }
}
