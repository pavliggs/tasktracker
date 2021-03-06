package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * Объект, содержащий полные данные о связанной задаче и выводимые в пользовательском интерфейсе
 *
 * @author Oleg Kiselev
 */
@Data
@AllArgsConstructor
public class RelatedTaskDtoFull {

    @Null(groups = Create.class)
    private Long id;

    @NotNull(groups = Create.class)
    private RelationTypeDtoPreview relationType;

    @NotNull(groups = Create.class)
    private Long currentTaskId;

    @NotNull(groups = Create.class)
    private TaskDtoPreview attachedTask;

}
