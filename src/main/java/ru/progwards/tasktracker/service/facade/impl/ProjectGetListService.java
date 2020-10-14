package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.impl.ProjectEntityRepository;
import ru.progwards.tasktracker.service.converter.impl.ConverterProject;
import ru.progwards.tasktracker.service.facade.GetListService;
import ru.progwards.tasktracker.service.vo.Project;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class ProjectGetListService implements GetListService<Project> {

    @Autowired
    private ProjectEntityRepository repository;

    @Autowired
    private ConverterProject converter;

    @Override
    public Collection<Project> getList() {
        return repository.get().stream().map(e -> converter.toVo(e)).collect(Collectors.toList());
    }
}