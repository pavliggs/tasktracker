package ru.progwards.tasktracker.controller.objects;

import ru.progwards.tasktracker.model.*;
import ru.progwards.tasktracker.model.types.EstimateChange;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Random;

/**
 * Объекты Model для использования в методах тестирования
 *
 * @author Oleg Kiselev
 */
public class GetModel {

    /**
     * Метод генерации строки из случайных символов для использования в объектах,
     * где валидация не допускает использование не уникальных данных
     *
     * @return строка из случайных символов
     */
    public static String randomChar() {
        Random random = new Random();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            result.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return result.toString();
    }

    public static Project getProject() {
        return new Project(
                null,
                "Test project " + randomChar(),
                "Description Test project",
                randomChar(),
                null,
                ZonedDateTime.now(),
                Collections.emptyList(),
                Collections.emptyList(),
                0L,
                false
        );
    }

    public static RelationType getRelationType() {
        return new RelationType(
                null,
                "relation name",
                null,
                Collections.emptyList()
        );
    }

    public static Task getTask() {
        return new Task(
                null,
                randomChar(),
                "Test task",
                "Description task",
                null,
                null,
                null,
                null,
                null,
                ZonedDateTime.now(),
                null,
                null,
                null,
                null,
                null,
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                false
        );
    }

    public static TaskType getTaskType() {
        return new TaskType(
                null,
                null,
                null,
                "name " + randomChar(),
                Collections.emptyList()
        );
    }

    public static User getUser() {
        return new User(
                null,
                "Ivan",
                "mail@mail" + randomChar() + ".ru",
                "YqIe3i0S4co3XO8tW6bQ",
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList()
        );
    }

    public static WorkLog getWorkLog() {
        return new WorkLog(
                null,
                null,
                null,
                null,
                ZonedDateTime.now(),
                "Description workLog",
                EstimateChange.DONT_CHANGE,
                null
        );
    }

}
