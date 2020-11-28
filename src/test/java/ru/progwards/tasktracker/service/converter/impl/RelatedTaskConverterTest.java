package ru.progwards.tasktracker.service.converter.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;
import ru.progwards.tasktracker.repository.entity.RelationTypeEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.RelatedTask;
import ru.progwards.tasktracker.service.vo.RelationType;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * тестирование конвертера между valueObject <-> entity
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class RelatedTaskConverterTest {

    @Autowired
    private Converter<RelatedTaskEntity, RelatedTask> converter;

    @Test
    void toVo_return_Null() {
        RelatedTask task = converter.toVo(null);

        assertThat(task, is(nullValue()));
    }

    @Test
    void toVo_return_Not_Null() {
        RelatedTask task = converter.toVo(
                new RelatedTaskEntity(
                        null, new RelationTypeEntity(1L, "блокирующая", null),
                        1L, null)
        );

        assertThat(task, is(notNullValue()));
    }

    @Test
    void toEntity_return_Null() {
        RelatedTaskEntity taskEntity = converter.toEntity(null);

        assertThat(taskEntity, is(nullValue()));
    }

    @Test
    void toEntity_return_Not_Null() {
        RelatedTaskEntity taskEntity = converter.toEntity(
                new RelatedTask(
                        null, new RelationType(1L, "блокирующая", null),
                        1L, null)
        );

        assertThat(taskEntity, is(notNullValue()));
    }
}