package me.bhradec.jtasks.service;

import me.bhradec.jtasks.dto.TaskDto;
import me.bhradec.jtasks.dto.command.TaskCommandDto;
import me.bhradec.jtasks.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    TaskDto save(TaskCommandDto taskCommandDto) throws NotFoundException;

    List<TaskDto> findAll();

    List<TaskDto> findAllByUserId(Long userId);

    Optional<TaskDto> findById(Long taskId);

    TaskDto updateById(Long taskId, TaskCommandDto taskCommandDto) throws NotFoundException;

    void deleteById(Long taskId) throws NotFoundException;
}
