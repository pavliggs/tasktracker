package ru.progwards.tasktracker.repository.dao.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.RepositoryUpdateField;
import ru.progwards.tasktracker.repository.entity.UserEntity;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;

import java.lang.reflect.Field;

@Component
public class UserEntityRepositoryUpdateField implements RepositoryUpdateField {

    private final UserEntityRepository repository;

    public UserEntityRepositoryUpdateField(UserEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public void updateField(UpdateOneValue oneValue) {
        UserEntity entity = repository.get(oneValue.getId());
        String field = oneValue.getFieldName();

        Class<UserEntity> clazz = UserEntity.class;

        for (Field f : clazz.getDeclaredFields()) {
            if (f.getName().equals(field)) {
                f.setAccessible(true);
                try {
                    f.set(entity, oneValue.getNewValue());
                    repository.update(entity);
                    break;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
