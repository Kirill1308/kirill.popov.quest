package quest.io;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import quest.model.Question;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

public class JSONQuestionReader {
    private static final String QUESTIONS_JSON = "C:\\Users\\kpopo\\IdeaProjects\\kirill.popov.quest\\src\\main\\resources\\questions.json";
    private final Gson gson = new Gson();

    public List<Question> readQuestionsFromJSON() {

        try (Reader reader = new FileReader(QUESTIONS_JSON)) {
            Type type = new TypeToken<List<Question>>() {
            }.getType();
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}