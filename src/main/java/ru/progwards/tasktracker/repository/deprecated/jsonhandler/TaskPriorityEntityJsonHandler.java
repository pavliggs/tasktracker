//package ru.progwards.tasktracker.repository.deprecated.jsonhandler;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import org.springframework.stereotype.Repository;
//import ru.progwards.tasktracker.repository.deprecated.JsonHandler;
//import ru.progwards.tasktracker.repository.deprecated.entity.TaskPriorityEntity;
//
//import java.io.File;
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.net.URISyntaxException;
//import java.nio.file.Files;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.stream.Collectors;
//
///**
// * Методы записи объектов TaskPriorityEntity в JSON-файл
// * @author Pavel Khovaylo
// */
//@Repository
//public class TaskPriorityEntityJsonHandler implements JsonHandler<Long, TaskPriorityEntity> {
//    private static File PROJECT_PATH;
//    private final Map<Long, TaskPriorityEntity> map = new ConcurrentHashMap<>();
//
//    static {
//        try {
//            PROJECT_PATH = new File(Objects.requireNonNull(
//                    Thread.currentThread().getContextClassLoader()
//                            .getResource("data/taskpriority.json")).toURI());
//        } catch (NullPointerException | URISyntaxException e) {
//            //e.printStackTrace();
//            PROJECT_PATH = new File("src/main/resources/data/taskpriority.json");
//        }
//    }
//
//    public TaskPriorityEntityJsonHandler() {
//        try {
//            read();
//        } catch (Exception ex1) {
//            try {
//                write();
//            } catch (Exception ex2) {
//                ex2.printStackTrace();
//            }
//        }
//    }
//
//    public Map<Long, TaskPriorityEntity> getMap() {
//        return map;
//    }
//
//    @Override
//    public void write() {
//        synchronized (this) {
//            List<TaskPriorityEntity> list = map.values().stream().collect(Collectors.toUnmodifiableList());
//            String json = new Gson().toJson(list);
//            try {
//                Files.writeString(PROJECT_PATH.toPath(), json);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public void read() {
//        synchronized (this) {
//            try {
//                map.clear();
//                String json = Files.readString(PROJECT_PATH.toPath());
//                Type type = new TypeToken<ArrayList<TaskPriorityEntity>>(){}.getType();
//                List<TaskPriorityEntity> list = new Gson().fromJson(json, type);
//                list.forEach(e -> map.put(e.getId(), e));
//            } catch (IOException ex) {
//                ex.printStackTrace();
//                throw new RuntimeException(ex.getMessage());
//            }
//        }
//    }
//}