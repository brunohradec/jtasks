package me.bhradec.jtasks.mapper;

import me.bhradec.jtasks.domain.Task;
import me.bhradec.jtasks.domain.User;
import me.bhradec.jtasks.dto.TaskDto;
import me.bhradec.jtasks.dto.command.TaskCommandDto;
import me.bhradec.jtasks.exception.NotFoundException;
import me.bhradec.jtasks.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    private final UserRepository userRepository;

    public TaskMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Task mapCommandToTask(TaskCommandDto taskCommandDto) throws NotFoundException {
        User user = userRepository
                .findById(taskCommandDto.getUserId())
                .orElseThrow(() -> new NotFoundException("User with the provided id not found."));

        return Task
                .builder()
                .name(taskCommandDto.getName())
                .description(taskCommandDto.getDescription())
                .startTime(taskCommandDto.getStartTime())
                .deadline(taskCommandDto.getDeadline())
                .user(user)
                .build();
    }

    public TaskDto mapTaskToDto(Task task) {
        return TaskDto
                .builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .startTime(task.getStartTime())
                .deadline(task.getDeadline())
                .userId(task.getUser().getId())
                .build();
    }
}
