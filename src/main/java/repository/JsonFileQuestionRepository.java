package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Question;
import model.QuestionType;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JsonFileQuestionRepository implements QuestionRepository {

    //TODO: not use full path
    private final List<Question> db;

    public JsonFileQuestionRepository(String jsonFilePath) {
        this.db = init(jsonFilePath);
    }

    private static List<Question> init(String jsonFilePath) {
        final Gson gson = new Gson();

        try (Reader reader = new FileReader(jsonFilePath)) {
            Type type = new TypeToken<List<Question>>() {
            }.getType();
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Question> findAll() {
        return Collections.unmodifiableList(db);
    }

    @Override
    public Optional<Question> findById(String id) {
        return db.stream().filter(x -> x.getId().equals(id)).findAny();
    }

    @Override
    public List<Question> findByQuestionType(QuestionType type) {
        return Collections.emptyList();
    }
}
