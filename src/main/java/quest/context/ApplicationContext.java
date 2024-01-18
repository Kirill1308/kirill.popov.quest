package quest.context;

import quest.exception.InstanceNotFoundException;
import quest.repository.JsonFileQuestionRepository;
import quest.repository.QuestionRepository;
import quest.repository.UserRepository;
import quest.repository.UserRepositoryImpl;
import quest.service.QuizService;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

public final class ApplicationContext {
    private static final QuestionRepository QUESTION_REPOSITORY = new JsonFileQuestionRepository();
    private static final UserRepository USER_REPOSITORY = new UserRepositoryImpl();
    private static final QuizService QUESTION_SERVICE = new QuizService(QUESTION_REPOSITORY);
    private static final Map<Class<?>, Object> CLASS_TO_OBJECT_INSTANCE = new HashMap<>();

    private ApplicationContext() {
    }

    static {
        CLASS_TO_OBJECT_INSTANCE.put(QuestionRepository.class, QUESTION_REPOSITORY);
        CLASS_TO_OBJECT_INSTANCE.put(UserRepository.class, USER_REPOSITORY);
        CLASS_TO_OBJECT_INSTANCE.put(QuizService.class, QUESTION_SERVICE);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getInstanceOf(Class<T> aClass) {
        Object object = CLASS_TO_OBJECT_INSTANCE.get(aClass);
        if (isNull(object)) {
            throw new InstanceNotFoundException("No instance found for class " + aClass.getName());
        }
        return (T) object;
    }
}
