//package ru.progwards.tasktracker.repository.deprecated.jsonhandler;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import ru.progwards.tasktracker.repository.deprecated.JsonHandler;
//import ru.progwards.tasktracker.repository.deprecated.entity.ProjectEntity;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@SpringBootTest
//public class ProjectEntityJsonHandlerTest {
//
//    @Mock
//    private JsonHandler<Long, ProjectEntity> projectEntityJsonHandler;
//
//    @BeforeEach
//    public void initMock() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    // проверка размеров getMap() до записи в файл и после чтения из файла
//    @Test
//    final void writeReadTest() {
//
//        Map<Long, ProjectEntity> map = new ConcurrentHashMap<>();
//
//        for (long i = 0; i < 10; i++) {
//            map.put(i,new ProjectEntity(i, "name"+i, "description"+i, "prefix"+i, i, 1000L, 0L));
//        }
//
//        Mockito.when(projectEntityJsonHandler.getMap()).thenReturn(map);
//
//        int beforeSize = projectEntityJsonHandler.getMap().size();
//        projectEntityJsonHandler.write();
//
//        projectEntityJsonHandler.read();
//        int afterSize = projectEntityJsonHandler.getMap().size();
//
//        Assertions.assertEquals(beforeSize, afterSize);
//    }
//}
