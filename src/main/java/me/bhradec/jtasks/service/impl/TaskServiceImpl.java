package me.bhradec.jtasks.service.impl;

import me.bhradec.jtasks.domain.Task;
import me.bhradec.jtasks.dto.TaskDto;
import me.bhradec.jtasks.dto.command.TaskCommandDto;
import me.bhradec.jtasks.exception.NotFoundException;
import me.bhradec.jtasks.mapper.TaskMapper;
import me.bhradec.jtasks.repository.TaskRepository;
import me.bhradec.jtasks.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskDto save(TaskCommandDto taskCommandDto) throws NotFoundException {
        return taskMapper.mapTaskToDto(taskRepository.save(taskMapper.mapCommandToTask(taskCommandDto)));
    }

    @Override
    public List<TaskDto> findAll() {
        return taskRepository
                .findAll()
                .stream()
                .map(taskMapper::mapTaskToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> findAllByUserId(Long userId) {
        return taskRepository
                .findAllByUserId(userId)
                .stream()
                .map(taskMapper::mapTaskToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TaskDto> findById(Long taskId) {
        return taskRepository
                .findById(taskId)
                .map(taskMapper::mapTaskToDto);
    }

    @Override
    public TaskDto updateById(Long taskId, TaskCommandDto taskCommandDto) throws NotFoundException {
        Task updatedTask = taskMapper.mapCommandToTask(taskCommandDto);

        Task task = taskRepository
                .findById(taskId)
                .orElseThrow(() -> new NotFoundException("Tasks with the provided id does not exist."));

        updatedTask.setId(task.getId());

        return taskMapper.mapTaskToDto(taskRepository.save(updatedTask));
    }

    @Override
    public void deleteById(Long taskId) throws NotFoundException {
        taskRepository
                .findById(taskId)
                .orElseThrow(() -> new NotFoundException("Tasks with the provided id does not exist."));

        taskRepository.deleteById(taskId);
    }
}
