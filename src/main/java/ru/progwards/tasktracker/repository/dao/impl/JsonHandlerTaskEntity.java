package ru.progwards.tasktracker.repository.dao.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.entity.TaskEntity;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class JsonHandlerTaskEntity implements JsonHandler {

    public final Map<Long, TaskEntity> tasks = new ConcurrentHashMap<>();
    private final static String TASKS_PATH
            = "C:\\Intellij Idea\\programming\\tasktracker\\src\\main\\java\\ru\\progwards\\tasktracker\\repository\\dao\\impl\\tasks.json";

    /**
     * записываем объекты в Json файл из Map
     */
    @Override
    public void write() {
        synchronized (this) {
            try (Writer writer = new FileWriter(TASKS_PATH)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(tasks.values().stream().collect(Collectors.toUnmodifiableList()), writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * читаем объекты из Json файла в Map
     */
    @Override
    public void read() {
        synchronized (this) {
            try {
                tasks.clear();
                String json = Files.readString(Path.of(TASKS_PATH));
                Type type = new TypeToken<List<TaskEntity>>() {
                }.getType();
                ArrayList<TaskEntity> list = new Gson().fromJson(json, type);
                list.forEach(e -> tasks.put(e.getId(), e));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
