package ru.progwards.tasktracker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.entity.RelationTypeEntity;
import ru.progwards.tasktracker.repository.deprecated.converter.Converter;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.model.RelatedTask;
import ru.progwards.tasktracker.model.RelationType;

import java.util.Collection;
import java.util.stream.Collectors;


/**
 * Бизнес-логика типа отношения связанных задач
 *
 * @author Oleg Kiselev
 */
@Service
public class RelationTypeService implements GetService<Long, RelationType>,
        CreateService<RelationType>, RemoveService<RelationType>,
        RefreshService<RelationType>, GetListService<RelationType> {

    @Autowired
    private Repository<Long, RelationTypeEntity> repository;
    @Autowired
    private Converter<RelationTypeEntity, RelationType> converter;
    @Autowired
    private GetListService<RelatedTask> getListService;

    /**
     * Метод получения типа отношения связанных задач
     *
     * @param id идентификатор типа отношения
     * @return полученный тип отношения
     */
    @Override
    public RelationType get(Long id) {
        return id == null ? null : converter.toVo(repository.get(id));
    }

    /**
     * Метод получения коллекции типов отношений связанных задач
     *
     * @return коллекция типов отношений
     */
    @Override
    public Collection<RelationType> getList() {
        return repository.get().stream()
                .map(entity -> converter.toVo(entity))
                .collect(Collectors.toList());
    }

    /**
     * Метод создания типа отношения связанных задач
     *
     * @param model создаваемый тип отношения
     */
    @Override
    public void create(RelationType model) {
        RelationTypeEntity entity = converter.toEntity(model);
        repository.create(entity);
        model.setId(entity.getId());
    }

    /**
     * Метод обновления типа отношения связанных задач
     *
     * @param model обновляемый тип отношения
     */
    @Override
    public void refresh(RelationType model) {
        repository.update(converter.toEntity(model));
    }

    /**
     * Метод обновления типа отношения связанных задач
     *
     * @param model удаляемый тип отношения
     */
    @Override
    public void remove(RelationType model) {
        if (checkingOtherDependenciesRelationType(model.getId()))
            throw new OperationIsNotPossibleException("Удаление невозможно, данный RelationType используется!");
        repository.delete(model.getId());
    }

    public boolean checkingOtherDependenciesRelationType(Long id) {//TODO - при переходе на Hibernate подумать об оптимизации
        return getListService.getList().stream()
                .anyMatch(relatedTask -> relatedTask.getRelationType().getId().equals(id));
    }
}