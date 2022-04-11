package me.bhradec.jtasks.controller;

import me.bhradec.jtasks.dto.UserDto;
import me.bhradec.jtasks.dto.command.UserCommandDto;
import me.bhradec.jtasks.exception.ConflictException;
import me.bhradec.jtasks.exception.NotFoundException;
import me.bhradec.jtasks.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> save(@Valid @RequestBody UserCommandDto userCommandDto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(userService.save(userCommandDto));
        } catch (ConflictException exception) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    exception.getMessage()
            );
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    exception.getMessage()
            );
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findAll());
    }

    @GetMapping(params = {"teamId"})
    public ResponseEntity<List<UserDto>> findAllByTeamId(@RequestParam Long teamId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findAllByTeamId(teamId));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long userId) {
        return userService
                .findById(userId)
                .map(userDto -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(userDto))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User with the provided id not found."
                ));
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateById(
            @PathVariable Long userId,
            @Valid @RequestBody UserCommandDto userCommandDto) {

        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userService.updateById(userId, userCommandDto));
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    exception.getMessage()
            );
        } catch (ConflictException exception) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    exception.getMessage()
            );
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long userId) {
        try {
            userService.deleteById(userId);
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
