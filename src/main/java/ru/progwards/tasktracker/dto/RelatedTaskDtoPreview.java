package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Объект, содержащий краткие данные о связанной задаче и выводимые в пользовательском интерфейсе
 *
 * @author Oleg Kiselev
 */
@Data
@AllArgsConstructor
public class RelatedTaskDtoPreview {

    @Min(0)
    @NotNull
    private Long id;

    private RelationTypeDtoPreview relationType;

    private Long currentTaskId;

    private TaskDtoPreview attachedTask;

}
