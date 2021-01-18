package ru.progwards.tasktracker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.progwards.tasktracker.dto.TaskTypeDtoFull;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.model.TaskType;
import ru.progwards.tasktracker.repository.ProjectRepository;
import ru.progwards.tasktracker.repository.TaskTypeRepository;

import javax.validation.ConstraintViolationException;
import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тестирование методов контроллера TaskTypeController
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@TestPropertySource(locations = "classpath:application-dev.properties")
@ActiveProfiles("dev")
class TaskTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TaskTypeRepository taskTypeRepository;
    @Autowired
    private ProjectRepository projectRepository;

    private static final String GET_PATH = "/rest/tasktype/";
    private static final String GET_LIST_PATH = "/rest/tasktype/list";
    private static final String GET_LIST_BY_PROJECT_PATH = "/rest/tasktype/{id}/list";
    private static final String CREATE_PATH = "/rest/tasktype/create";
    private static final String DELETE_PATH = "/rest/tasktype/{id}/delete";
    private static final String UPDATE_PATH = "/rest/tasktype/{id}/update";

    public TaskTypeDtoFull getTaskTypeDto() {
        return new TaskTypeDtoFull(
                null,
                null,
                null,
                "type name"
        );
    }

    public TaskType getTaskType() {
        return new TaskType(
                null,
                null,
                null,
                "type name",
                null
        );
    }

    public Project getProject() {
        return new Project(
                null,
                "Test project",
                "Description Test project",
                "TP",
                null,
                ZonedDateTime.now(),
                null,
                null,
                0L,
                false
        );
    }

    public static MockHttpServletRequestBuilder postJson(String uri, Object body) {
        try {
            String json = new ObjectMapper().writeValueAsString(body);
            return post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static MockHttpServletRequestBuilder getUriAndMediaType(String uri) {
        return get(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

    public static MockHttpServletRequestBuilder getUriAndMediaType(String uri, Long id) {
        return get(uri.replace("{id}", String.valueOf(id)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

    public static MockHttpServletRequestBuilder deleteUriAndMediaType(String uri, Long id) {
        return delete(uri.replace("{id}", String.valueOf(id)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

    public static MockHttpServletRequestBuilder putJson(String uri, Long id, Object body) {
        try {
            String json = new ObjectMapper().writeValueAsString(body);
            return put(uri.replace("{id}", String.valueOf(id)))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(1)
    void create_TaskType() throws Exception {
        MvcResult result = mockMvc.perform(
                postJson(CREATE_PATH, getTaskTypeDto()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Long id = getResultId(result);

        try {
            mockMvc.perform(get(GET_PATH + id))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(id), Long.class))
                    .andExpect(jsonPath("$.name", equalTo("type name")));
        } finally {
            taskTypeRepository.deleteById(id);
        }
    }

    private Long getResultId(MvcResult result) throws UnsupportedEncodingException {
        String resultJson = result.getResponse().getContentAsString();
        return JsonPath.parse(resultJson).read("$.id", Long.class);
    }

    @Test
    @Order(2)
    void create_TaskType_BadRequest_Validation_If_Id_is_NotNull() throws Exception {
        TaskTypeDtoFull dto = getTaskTypeDto();
        dto.setId(1L);
        mockMvc.perform(
                postJson(CREATE_PATH, dto))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    @Order(3)
    void create_TaskType_BadRequest_Validation_If_Name_is_Empty() throws Exception {
        TaskTypeDtoFull dto = getTaskTypeDto();
        dto.setName("");
        mockMvc.perform(
                postJson(CREATE_PATH, dto))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    @Order(4)
    void create_TaskType_BadRequest_Validation_If_Name_is_Null() throws Exception {
        TaskTypeDtoFull dto = getTaskTypeDto();
        dto.setName(null);
        mockMvc.perform(
                postJson(CREATE_PATH, dto))
                .andExpect(status().isInternalServerError())
                .andExpect(mvcResult -> assertNotNull(mvcResult.getResolvedException()));
    }

    @Test
    @Order(5)
    void get_TaskType() throws Exception {
        TaskType tt = taskTypeRepository.save(getTaskType());

        try {
            mockMvc.perform(
                    getUriAndMediaType(GET_PATH + tt.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(tt.getId()), Long.class))
                    .andExpect(jsonPath("$.name", equalTo("type name")));
        } finally {
            taskTypeRepository.deleteById(tt.getId());
        }
    }

    @Test
    @Order(6)
    void get_TaskType_when_NotFound() throws Exception {
        mockMvc.perform(
                getUriAndMediaType(GET_PATH + 1L))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
    }

    @Test
    @Order(7)
    void get_TaskType_when_Id_is_negative() throws Exception {
        mockMvc.perform(
                getUriAndMediaType(GET_PATH + (-1L)))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof ConstraintViolationException));
    }

    @Test
    @Order(8)
    void get_TaskType_when_Id_more_value_Long() throws Exception {
        mockMvc.perform(
                getUriAndMediaType(GET_PATH + (Long.MAX_VALUE + 1)))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof ConstraintViolationException));
    }

    @Test
    @Order(9)
    void getList_TaskType() throws Exception {
        TaskType one = getTaskType();
        one.setName("name one");
        TaskType two = getTaskType();
        two.setName("name two");
        List<TaskType> listType = List.of(one, two);
        taskTypeRepository.saveAll(listType);

        try {
            mockMvc.perform(
                    getUriAndMediaType(GET_LIST_PATH))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[*].name", containsInAnyOrder("name one", "name two")));
        } finally {
            taskTypeRepository.deleteAll(listType);
        }
    }

    @Test
    @Order(10)
    void getList_TaskType_when_return_Empty_List() throws Exception {
        mockMvc.perform(
                getUriAndMediaType(GET_LIST_PATH))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
    }

    @Test
    @Order(11)
    void getListByProject_TaskType() throws Exception {
        Project project = getProject();
        projectRepository.save(project);

        TaskType one = getTaskType();
        one.setName("name one");
        one.setProject(project);

        TaskType two = getTaskType();
        two.setName("name two");
        two.setProject(project);

        List<TaskType> listType = List.of(one, two);
        taskTypeRepository.saveAll(listType);

        try {
            mockMvc.perform(
                    getUriAndMediaType(GET_LIST_BY_PROJECT_PATH, project.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[*].name", containsInAnyOrder("name one", "name two")));
        } finally {
            taskTypeRepository.deleteAll(listType);
            projectRepository.deleteById(project.getId());
        }
    }

    @Test
    @Order(12)
    void getListByProject_TaskType_when_Id_is_negative() throws Exception {
        mockMvc.perform(
                getUriAndMediaType(GET_LIST_BY_PROJECT_PATH, -1L))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof ConstraintViolationException));
    }

    @Test
    @Order(13)
    void getListByProject_TaskType_when_Id_more_value_Long() throws Exception {
        mockMvc.perform(
                getUriAndMediaType(GET_LIST_BY_PROJECT_PATH, Long.MAX_VALUE + 1))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof ConstraintViolationException));
    }

    @Test
    @Order(14)
    void getListByProject_TaskType_when_return_Empty_List() throws Exception {
        Project project = getProject();
        projectRepository.save(project);

        try {
            mockMvc.perform(
                    getUriAndMediaType(GET_LIST_BY_PROJECT_PATH, project.getId()))
                    .andExpect(status().isNotFound())
                    .andExpect(mvcResult ->
                            assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
        } finally {
            projectRepository.deleteById(project.getId());
        }
    }

    @Test
    @Order(15)
    void delete_TaskType() {
        TaskType tt = taskTypeRepository.save(getTaskType());

        try {
            mockMvc.perform(
                    deleteUriAndMediaType(DELETE_PATH, tt.getId()))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            taskTypeRepository.deleteById(tt.getId());
        }
    }

    @Test
    @Order(16)
    void delete_TaskType_when_Id_is_negative() throws Exception {
        mockMvc.perform(
                deleteUriAndMediaType(DELETE_PATH, -1L))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof ConstraintViolationException));
    }

    @Test
    @Order(17)
    void delete_TaskType_when_Id_more_value_Long() throws Exception {
        mockMvc.perform(
                deleteUriAndMediaType(DELETE_PATH, Long.MAX_VALUE + 1))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof ConstraintViolationException));
    }

    @Test
    @Order(18)
    void update_TaskType() throws Exception {
        TaskType tt = taskTypeRepository.save(getTaskType());
        TaskTypeDtoFull dto = getTaskTypeDto();
        dto.setName("updated name");
        dto.setId(tt.getId());

        MvcResult result = mockMvc.perform(
                putJson(UPDATE_PATH, tt.getId(), dto))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Long id = getResultId(result);

        try {
            mockMvc.perform(get(GET_PATH + id))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(id), Long.class))
                    .andExpect(jsonPath("$.name", equalTo("updated name")));
        } finally {
            taskTypeRepository.deleteById(id);
        }
    }

    @Test
    @Order(19)
    void update_TaskType_when_Request_Id_is_different_Dto_Id() throws Exception {
        TaskType tt = taskTypeRepository.save(getTaskType());
        TaskTypeDtoFull dto = getTaskTypeDto();
        dto.setName("another name");
        dto.setId(tt.getId() + 1);

        try {
            mockMvc.perform(
                    putJson(UPDATE_PATH, tt.getId(), dto))
                    .andExpect(status().isBadRequest())
                    .andExpect(mvcResult ->
                            assertTrue(mvcResult.getResolvedException() instanceof BadRequestException));
        } finally {
            taskTypeRepository.deleteById(tt.getId());
        }
    }

    @Test
    @Order(20)
    void update_TaskType_when_Name_is_already_used_another_TaskType() throws Exception {
        TaskType tt = taskTypeRepository.save(getTaskType());
        TaskTypeDtoFull dto = getTaskTypeDto();
        dto.setId(tt.getId() + 1);

        try {
            mockMvc.perform(
                    putJson(UPDATE_PATH, tt.getId(), dto))
                    .andExpect(status().isBadRequest())
                    .andExpect(mvcResult -> assertNotNull(mvcResult.getResolvedException()));
        } finally {
            taskTypeRepository.deleteById(tt.getId());
        }
    }

    @Test
    @Order(21)
    void update_TaskType_when_NotFound() throws Exception {
        TaskTypeDtoFull dto = getTaskTypeDto();
        dto.setId(1L);

        mockMvc.perform(
                putJson(UPDATE_PATH, 1L, dto))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
    }
}