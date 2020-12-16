package ru.progwards.tasktracker.service.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.WorkFlowEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.GetListByParentService;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.WorkFlow;
import ru.progwards.tasktracker.service.vo.WorkFlowStatus;

import java.util.ArrayList;
import java.util.List;


/**
 * Бизнес процесс
 * Преобразование valueObject <-> entity
 *
 * WorkFlow <-> WorkFlowEntity
 *
 * @author Gregory Lobkov
 */
@Component
public class WorkFlowConverter implements Converter<WorkFlowEntity, WorkFlow> {

    /**
     * Сервис получения статусов Workflow
     */
    @Autowired
    private GetService<Long, WorkFlowStatus> statusGetService;

    /**
     * Сервис получения статусов Workflow по бизнес-процессу
     */
    @Autowired
    private GetListByParentService<Long, WorkFlowStatus> statusGetListByParentService;


    /**
     * Преобразовать в бизнес-объект
     *
     * @param entity сущность репозитория
     * @return бизнес-объект
     */
    @Override
    public WorkFlow toVo(WorkFlowEntity entity) {
//        WorkFlowStatus workFlowStatus = statusGetService.get(entity.getStart_status_id());
//        List<WorkFlowStatus> statuses = new ArrayList(statusGetListByParentService.getListByParentId(entity.getId()));
//        return new WorkFlow(entity.getId(), entity.getName(), entity.isPattern(),
//                entity.getStart_status_id(), workFlowStatus, statuses);
        return null;
    }


    /**
     * Преобразовать в сущность хранилища
     *
     * @param vo бизнес-объект
     * @return сущность репозитория
     */
    @Override
    public WorkFlowEntity toEntity(WorkFlow vo) {
//        return new WorkFlowEntity(vo.getId(), vo.getName(), vo.isPattern(), vo.getStart_status_id());
        return null;
    }

}