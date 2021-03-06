package ru.progwards.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.model.TaskPriority;

/**
 * @author Pavel Khovaylo
 */
@Repository
@Transactional(readOnly = true)
public interface TaskPriorityRepository extends JpaRepository<TaskPriority, Long> {



}
