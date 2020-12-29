package ru.progwards.tasktracker.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * DtoFull для TaskPriority
 * @author Pavel Khovaylo
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskPriorityDtoFull {
    /**
     * идентификатор
     */
    Long id;
    /**
     * имя
     */
    String name;
    /**
     * числовой приоритет
     */
    Integer value;
}