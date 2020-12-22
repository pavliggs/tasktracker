package ru.progwards.tasktracker.service.impl.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.entity.ProjectEntity;
import ru.progwards.tasktracker.repository.deprecated.converter.Converter;
import ru.progwards.tasktracker.service.GetListByProjectService;
import ru.progwards.tasktracker.service.RemoveService;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.model.Task;

/**
 * Класс по удалению проекта
 * @author Pavel Khovaylo
 */
@Service
public class ProjectRemoveService implements RemoveService<Project> {

    /**
     * репозиторий с проектами
     */
    @Autowired
    private Repository<Long, ProjectEntity> repository;
    /**
     * конвертер проектов
     */
    @Autowired
    private Converter<ProjectEntity, Project> converter;
    /**
     * для получения Task, относящихся к проекту
     */
    @Autowired
    private GetListByProjectService<Long, Task> taskGetListByProjectService;

    /**
     * метод по удалению проекта
     * @param model бизнес-модель проекта, которую необходимо удалить
     */
    @Override
    public void remove(Project model) {
        Project project = converter.toVo(repository.get(model.getId()));

        // если спискок задач у проекта пустой, то удаляем проект; если какие-то задачи есть, то выводим исключение
        if (taskGetListByProjectService.getListByProjectId(project.getId()).size() == 0) {
            ProjectEntity entity = converter.toEntity(model);
            // устанавливаем флажок, который означает, что проект удалён
            entity.setDeleted(true);
            repository.update(entity);
//            repository.delete(model.getId());
        } else
            throw new OperationIsNotPossibleException("Project with id = " + model.getId() +
                    " have tasks. Delete not possible");
    }
}