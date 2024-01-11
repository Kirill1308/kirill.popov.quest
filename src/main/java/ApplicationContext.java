import exception.ApllicationContextInstanceCreationException;
import repository.JsonFileQuestionRepository;
import repository.QuestionRepository;
import repository.SQLQuestionRepository;
import service.QuestionService;

import java.util.HashMap;
import java.util.Map;

public final class ApplicationContext {

    private static final QuestionRepository QUESTION_REPOSITORY =
            new SQLQuestionRepository();
    private static final QuestionService QUESTION_SERVICE =
            new QuestionService(QUESTION_REPOSITORY);

    private static final Map<Class<?>, Object> CLASS_TO_OBJECT_INSTANCE =
            new HashMap<>();

    static {
        CLASS_TO_OBJECT_INSTANCE.put(QuestionRepository.class, QUESTION_REPOSITORY);
        CLASS_TO_OBJECT_INSTANCE.put(QuestionService.class, QUESTION_SERVICE);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getInstanceOf(Class<T> aClass) {
        Object object = CLASS_TO_OBJECT_INSTANCE.get(aClass);
        if (object == null) {
            throw new ApllicationContextInstanceCreationException(aClass);
        }
        return (T) object;
    }


}
