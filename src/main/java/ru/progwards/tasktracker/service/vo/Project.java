package ru.progwards.tasktracker.service.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Класс Project - бизнес-модель проекта
 * @author Progwards
 * @version 1.0
 */
public class Project  {
    /**
     * идентификатор проекта
     */
    private Long id;
    /**
     * имя проекта
     */
    private String name;
    /**
     * описание проекта
     */
    private String description;
    /**
     * уникальная аббревиатура, созданная на основании имени проекта
     */
    private String prefix;
    /**
     * владелец (создатель) проекта
     */
    private User owner;
    /**
     * время создания проекта
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private ZonedDateTime created;
    /**
     * стадия разработки, в которой находится проект
     */
    private WorkFlow workFlow;
    /**
     * список задач, относящихся к данному проекту
     */
    List<Task> tasks;

    public Project(Long id, String name, String description, User owner,
                   ZonedDateTime created, WorkFlow workFlow, List<Task> tasks) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.prefix = getPrefix(name);
        this.owner = owner;
        this.created = created;
        this.workFlow = workFlow;
        this.tasks = tasks;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrefix() {
        return prefix;
    }

    public User getOwner() {
        return owner;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public WorkFlow getWorkFlow() {
        return workFlow;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public static String getPrefix(String name) {
        String[] items = name.split("\\s+");
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(items).forEach(e -> stringBuilder.append(e.substring(0, 1).toUpperCase()));
        return stringBuilder.toString();
    }
}