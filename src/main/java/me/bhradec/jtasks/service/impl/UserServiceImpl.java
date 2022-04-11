package me.bhradec.jtasks.service.impl;

import me.bhradec.jtasks.domain.User;
import me.bhradec.jtasks.dto.UserDto;
import me.bhradec.jtasks.dto.command.UserCommandDto;
import me.bhradec.jtasks.exception.ConflictException;
import me.bhradec.jtasks.exception.NotFoundException;
import me.bhradec.jtasks.mapper.UserMapper;
import me.bhradec.jtasks.repository.UserRepository;
import me.bhradec.jtasks.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto save(UserCommandDto userCommand) throws ConflictException, NotFoundException {
        User user = userMapper.mapCommandToUser(userCommand);

        if (userRepository.findFirstByUsername(user.getUsername()).isPresent()) {
            throw new ConflictException("User with the provided username already exists.");
        }

        if (userRepository.findFirstByEmail(user.getEmail()).isPresent()) {
            throw new ConflictException("User with the provided email already exists.");
        }

        return userMapper.mapUserToDto(userRepository.save(user));
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::mapUserToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findAllByTeamId(Long teamId) {
        return userRepository
                .findAllByTeamId(teamId)
                .stream()
                .map(userMapper::mapUserToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> findById(Long userId) {
        return userRepository
                .findById(userId)
                .map(userMapper::mapUserToDto);
    }

    @Override
    public UserDto updateById(Long userId, UserCommandDto userCommand) throws NotFoundException, ConflictException {
        User updatedUser = userMapper.mapCommandToUser(userCommand);

        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException("User with the provided id does not exist."));

        updatedUser.setId(user.getId());

        if (!user.getUsername().equals(updatedUser.getUsername())
                && userRepository.findFirstByUsername(user.getUsername()).isPresent()) {
            throw new ConflictException("User with the provided username already exists.");
        }

        if (!user.getEmail().equals(updatedUser.getEmail())
                && userRepository.findFirstByEmail(user.getEmail()).isPresent()) {
            throw new ConflictException("User with the provided email already exists.");
        }

        return userMapper.mapUserToDto(userRepository.save(updatedUser));
    }

    @Override
    public void deleteById(Long userId) throws NotFoundException {
        userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException("User with the provided id does not exist."));

        userRepository.deleteById(userId);
    }
}
