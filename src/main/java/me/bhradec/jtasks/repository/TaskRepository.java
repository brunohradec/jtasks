package me.bhradec.jtasks.repository;

import me.bhradec.jtasks.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUserId(Long userId);
}
