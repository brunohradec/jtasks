package me.bhradec.jtasks.service;

import me.bhradec.jtasks.dto.UserDto;
import me.bhradec.jtasks.dto.command.UserCommandDto;
import me.bhradec.jtasks.exception.ConflictException;
import me.bhradec.jtasks.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDto save(UserCommandDto userCommand) throws ConflictException, NotFoundException;

    List<UserDto> findAll();

    List<UserDto> findAllByTeamId(Long teamId);

    Optional<UserDto> findById(Long userId);

    UserDto updateById(Long userId, UserCommandDto userCommand) throws NotFoundException, ConflictException;

    void deleteById(Long userId) throws NotFoundException;
}
