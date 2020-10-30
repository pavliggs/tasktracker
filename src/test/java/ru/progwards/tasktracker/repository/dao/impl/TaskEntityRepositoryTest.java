package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.util.types.TaskType;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TaskEntityRepositoryTest {

    @Mock
    private Repository<Long, TaskEntity> taskRepository;

    @Test
    public void testGetAllTaskEntity() {
        when(taskRepository.get()).thenReturn(Arrays.asList(
                new TaskEntity(1L, "TT1-1", "Test task 1 TEST", "Description task 1",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        null,
                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false),
                new TaskEntity(2L, "TT2-2", "Test task 2 TEST", "Description task 2",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        null,
                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false)
        ));

        Collection<TaskEntity> tempList = taskRepository.get();

        assertEquals(2, tempList.size());
        assertNotNull(tempList);
    }

    @Test
    public void testGetAllTaskEntity_Return_Empty_Collection(){
        when(taskRepository.get()).thenReturn(Collections.emptyList());

        Collection<TaskEntity> collection = taskRepository.get();

        assertTrue(collection.isEmpty());
    }

    @Test
    public void testGetOneTaskEntity() {
        when(taskRepository.get(anyLong())).thenReturn(
                new TaskEntity(1L, "TT1-1", "Test task 1 TEST", "Description task 1",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        null,
                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false)
        );

        TaskEntity taskEntity = taskRepository.get(1L);

        assertNotNull(taskEntity);
        assertEquals("Test task 1 TEST", taskEntity.getName());
    }

    @Test
    public void testGetOneTaskEntity_Return_Null(){
        when(taskRepository.get(anyLong())).thenReturn(null);

        TaskEntity taskEntity = taskRepository.get(1L);

        assertNull(taskEntity);
    }

    @Test
    public void testCreateTaskEntity() {
        TaskEntity task = new TaskEntity(1L, "TT1-1", "Test task 1 TEST", "Description task 1",
                TaskType.BUG, null, 11L, new User(), new User(),
                ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                null,
                Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false);

        taskRepository.create(task);

        verify(taskRepository, times(1)).create(task);
    }

    @Test
    public void testUpdateTaskEntity() {
        TaskEntity task = new TaskEntity(1L, "TT1-1", "Test task 1 TEST", "Description task 1",
                TaskType.BUG, null, 11L, new User(), new User(),
                ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                null,
                Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false);

        taskRepository.update(task);
        verify(taskRepository, times(1)).update(task);
    }

    @Test
    public void testDeleteTaskEntity() {
        taskRepository.delete(1L);

        assertNull(taskRepository.get(1L));
        verify(taskRepository, times(1)).delete(1L);
    }
}