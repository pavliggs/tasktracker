package ru.progwards.tasktracker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.ZonedDateTime;

/**
 * Связка между задачами и вложениями
 * много ко многим
 *
 * @author Gregory Lobkov
 */
@Data
@AllArgsConstructor
public class TaskAttachmentDtoFull {

    /**
     * ID связки задача-вложение
     */
    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private Long id;

    /**
     * Задача, связанная с вложением
     */
    @NotEmpty
    private TaskDtoPreview task;

    /**
     * Полное имя файла-вложения
     */
    @NotEmpty
    private String name;

    /**
     * Расширение файла
     */
    @NotEmpty
    private String extension;

    /**
     * Размер в байтах
     */
    @NotNull
    private Long size;

    /**
     * Дата создания
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ZonedDateTime created;

    /**
     * Вложение
     */
//    @NotEmpty
//    private TaskAttachmentContentDtoPreview content;

}
