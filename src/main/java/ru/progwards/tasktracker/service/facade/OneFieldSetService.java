package ru.progwards.tasktracker.service.facade;

import ru.progwards.tasktracker.service.vo.UpdateOneValue;

public interface OneFieldSetService<T> {

    void setOneField(UpdateOneValue oneValue);

}
