package me.bhradec.jtasks.controller;

import me.bhradec.jtasks.dto.TaskDto;
import me.bhradec.jtasks.dto.command.TaskCommandDto;
import me.bhradec.jtasks.exception.NotFoundException;
import me.bhradec.jtasks.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskDto> save(@Valid @RequestBody TaskCommandDto taskCommandDto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(taskService.save(taskCommandDto));
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    exception.getMessage()
            );
        }
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.findAll());
    }

    @GetMapping(params = {"userId"})
    public ResponseEntity<List<TaskDto>> findAllByUserId(@RequestParam Long userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.findAllByUserId(userId));
    }

    @GetMapping("{taskId}")
    public ResponseEntity<TaskDto> findById(@PathVariable Long taskId) {
        return taskService
                .findById(taskId)
                .map(taskDto -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(taskDto))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Task with the provided id not found."
                ));
    }

    @PutMapping("{taskId}")
    public ResponseEntity<TaskDto> updateById(
            @PathVariable Long taskId,
            @Valid @RequestBody TaskCommandDto taskCommandDto) {

        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(taskService.updateById(taskId, taskCommandDto));
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    exception.getMessage()
            );
        }
    }

    @DeleteMapping("{taskId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long taskId) {
        try {
            taskService.deleteById(taskId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    exception.getMessage()
            );
        }
    }
}
