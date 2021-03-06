//package ru.progwards.tasktracker.repository.deprecated.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import ru.progwards.tasktracker.repository.deprecated.JsonHandler;
//import ru.progwards.tasktracker.repository.deprecated.Repository;
//import ru.progwards.tasktracker.repository.deprecated.RepositoryByAttachedTaskId;
//import ru.progwards.tasktracker.repository.deprecated.RepositoryByTaskId;
//import ru.progwards.tasktracker.repository.deprecated.entity.RelatedTaskEntity;
//
//import java.util.Collection;
//import java.util.stream.Collectors;
//
///**
// * Методы для работы с БД для сущности связанной задачи RelatedTaskEntity
// *
// * @author Oleg Kiselev
// */
//@org.springframework.stereotype.Repository
//@Deprecated
//public class RelatedTaskEntityRepository implements Repository<Long, RelatedTaskEntity>,
//        RepositoryByTaskId<Long, RelatedTaskEntity>, RepositoryByAttachedTaskId<Long, RelatedTaskEntity> {
//
//    @Autowired
//    private JsonHandler<Long, RelatedTaskEntity> jsonHandler;
//
//    /**
//     * Метод получения всех связанных задач из базы данных без привязки к какой-либо задаче
//     *
//     * @return коллекция сущностей
//     */
//    @Override
//    public Collection<RelatedTaskEntity> get() {
//        return jsonHandler.getMap().values().stream()
//                .filter(entity -> !entity.isDeleted())
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * Метод получения связанной задачи по её идентификатору
//     *
//     * @param id идентификатор связанной задачи
//     * @return связанную задачу
//     */
//    @Override
//    public RelatedTaskEntity get(Long id) {
//        RelatedTaskEntity relatedTask = jsonHandler.getMap().get(id);
//        return relatedTask == null || relatedTask.isDeleted() ? null : relatedTask;
//    }
//
//    /**
//     * Метод создания и записи в БД сущности RelatedTaskEntity
//     *
//     * @param entity сущность с данными связанной задачи
//     */
//    @Override
//    public void create(RelatedTaskEntity entity) {
//        RelatedTaskEntity relatedTask = jsonHandler.getMap().put(entity.getId(), entity);
//        if (relatedTask == null)
//            jsonHandler.write();
//    }
//
//    @Override
//    public void update(RelatedTaskEntity entity) {
//    }
//
//    /**
//     * Метод удаления связанных задач по идентификатору из параметра метода
//     *
//     * @param id идентификатор удаляемой сущности
//     */
//    @Override
//    public void delete(Long id) {
//        RelatedTaskEntity entity = jsonHandler.getMap().get(id);
//        if (entity != null) {
//            jsonHandler.getMap().remove(id);
//            entity.setDeleted(true);
//            create(entity);
//        }
//    }
//
//    /**
//     * Метод получения коллекции связанных задач по идентификатору задачи
//     *
//     * @param taskId идентификатор задачи для которой необходимо получить связанные задачи
//     * @return возвращается коллекция (может быть пустой) связанных задач
//     */
//    @Override
//    public Collection<RelatedTaskEntity> getByTaskId(Long taskId) {
//        return jsonHandler.getMap().values().stream()
//                .filter(entity -> entity.getCurrentTask().getId().equals(taskId) && !entity.isDeleted())
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * Метод получения всех входящих RelatedTaskEntity для определенной задачи
//     *
//     * @param taskId идентификатор задачи
//     * @return коллекция entity
//     */
//    @Override
//    public Collection<RelatedTaskEntity> getByAttachedTaskId(Long taskId) {
//        return jsonHandler.getMap().values().stream()
//                .filter(entity -> entity.getAttachedTask().getId().equals(taskId) && !entity.isDeleted())
//                .collect(Collectors.toList());
//    }
//}
