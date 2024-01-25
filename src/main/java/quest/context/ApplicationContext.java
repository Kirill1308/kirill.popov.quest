package quest.context;

import quest.command.ActionCommand;
import quest.command.UserAction;
import quest.exception.InstanceNotFoundException;
import quest.repository.JsonFileQuestionRepository;
import quest.repository.QuestionRepository;
import quest.repository.UserRepository;
import quest.repository.UserRepositoryImpl;
import quest.service.AuthenticationHandler;
import quest.service.SecurityService;
import quest.service.QuizService;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

public final class ApplicationContext {
    private static final QuestionRepository QUESTION_REPOSITORY = new JsonFileQuestionRepository();
    private static final UserRepositoryImpl USER_REPOSITORY = new UserRepositoryImpl();
    private static final QuizService QUESTION_SERVICE = new QuizService(QUESTION_REPOSITORY);
    private static final SecurityService SECURITY_SERVICE = new SecurityService();
    private static final AuthenticationHandler AUTHENTICATION_HANDLER = new AuthenticationHandler(USER_REPOSITORY, SECURITY_SERVICE);

    private static final Map<Class<?>, Object> CLASS_TO_OBJECT_INSTANCE = new HashMap<>();
    private static final Map<UserAction, ActionCommand> ACTION_COMMANDS = new EnumMap<>(UserAction.class);

    private ApplicationContext() {
    }

    static {
        CLASS_TO_OBJECT_INSTANCE.put(QuestionRepository.class, QUESTION_REPOSITORY);
        CLASS_TO_OBJECT_INSTANCE.put(UserRepositoryImpl.class, USER_REPOSITORY);
        CLASS_TO_OBJECT_INSTANCE.put(QuizService.class, QUESTION_SERVICE);
        CLASS_TO_OBJECT_INSTANCE.put(SecurityService.class, SECURITY_SERVICE);
        CLASS_TO_OBJECT_INSTANCE.put(AuthenticationHandler.class, AUTHENTICATION_HANDLER);

        ACTION_COMMANDS.put(UserAction.LOGIN, new quest.command.LoginCommand(AUTHENTICATION_HANDLER));
        ACTION_COMMANDS.put(UserAction.REGISTER, new quest.command.RegisterCommand(AUTHENTICATION_HANDLER));
    }

    @SuppressWarnings("unchecked")
    public static <T> T getInstanceOf(Class<T> aClass) {
        Object object = CLASS_TO_OBJECT_INSTANCE.get(aClass);
        if (isNull(object)) {
            throw new InstanceNotFoundException("No instance found for class " + aClass.getName());
        }
        return (T) object;
    }

    public static Map<UserAction, ActionCommand> getActionCommands() {
        return ACTION_COMMANDS;
    }
}
