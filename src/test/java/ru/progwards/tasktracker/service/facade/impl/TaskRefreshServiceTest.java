package ru.progwards.tasktracker.service.facade.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.util.types.Priority;
import ru.progwards.tasktracker.util.types.TaskType;
import ru.progwards.tasktracker.util.types.WorkflowStatus;

import java.time.ZonedDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class TaskRefreshServiceTest {

    @Mock
    private TaskRefreshService taskRefreshService;


    @Test
    public void testRefresh() {
        taskRefreshService.refresh(
                new Task(1L, "Testing_task1_test_updated", "description1", TaskType.BUG, Priority.MAJOR,
                        001L, 003L, ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
                        100, 0005L, "STR_CODE_TTT", WorkflowStatus.NEW, "new_version",
                        123456L, 123456L, 123456L)
        );

        verify(taskRefreshService, times(1)).refresh(any());
    }
}