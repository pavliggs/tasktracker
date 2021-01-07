package ru.progwards.tasktracker.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Project - бизнес-модель проекта
 * @author Pavel Khovaylo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "project")
//В SQL-запрос попадают только измененные поля
@DynamicUpdate
public class Project  {
    /**
     * идентификатор проекта
     */
    @Id
    @SequenceGenerator(name = "PROJECT_SEQ", sequenceName = "project_seq", allocationSize = 1)
    @GeneratedValue(generator = "PROJECT_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    Long id;
    /**
     * имя проекта
     */
    @NotEmpty
    @Basic
    @Column(name = "name", nullable = false, length = 100)
    String name;
    /**
     * описание проекта
     */
    @Basic
    @Column(name = "description", length = 800)
    String description;
    /**
     * уникальная аббревиатура, созданная на основании имени проекта
     */
    @NotEmpty
    @Basic
    @Column(name = "prefix", nullable = false, unique = true, length = 10)
    String prefix;
    /**
     * владелец (создатель) проекта
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    User owner;
    /**
     * время создания проекта
     */
    @NotNull
    @Column(name = "created", nullable = false)
    ZonedDateTime created;
    /**
     * список задач проекта
     */
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    List<Task> tasks;
    /**
     * список типов задач, относящихся к данному проекту
     */
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    List<TaskType> taskTypes;
    /**
     * хранит код последней добавленной задачи к данному проекту
     */
    @NotNull
    @Basic
    @Column(name = "last_task_code", nullable = false)
    Long lastTaskCode;
    /**
     * информация об удалении проекта
     */
    @NotNull
    @Basic
    @Column(name = "is_deleted", nullable = false)
    boolean deleted;
}