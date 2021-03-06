package ru.progwards.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.model.User;

import java.util.Optional;

/**
 * @author Oleg Kiselev
 */
@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Метод проверки существования email в БД
     *
     * @param email проверяемый электронный адрес
     * @return true - если email есть в БД и false - если нет
     */
    boolean existsByEmail(String email);

    /**
     * Метод получения пользователя по адресу электронной почты
     *
     * @param email электронный адрес
     * @return пользователь
     */
    Optional<User> findByEmail(String email);
}
