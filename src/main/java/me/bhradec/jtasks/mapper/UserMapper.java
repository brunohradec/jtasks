package me.bhradec.jtasks.mapper;

import me.bhradec.jtasks.domain.Team;
import me.bhradec.jtasks.domain.User;
import me.bhradec.jtasks.dto.UserDto;
import me.bhradec.jtasks.dto.command.UserCommandDto;
import me.bhradec.jtasks.exception.NotFoundException;
import me.bhradec.jtasks.repository.TeamRepository;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final TeamRepository teamRepository;

    public UserMapper(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public User mapCommandToUser(UserCommandDto userCommandDto) throws NotFoundException {
        Team team = teamRepository
                .findById(userCommandDto.getTeamId())
                .orElseThrow(() -> new NotFoundException("Team with the provided id not found."));

        return User
                .builder()
                .firstName(userCommandDto.getFirstName())
                .lastName(userCommandDto.getLastName())
                .username(userCommandDto.getUsername())
                .email(userCommandDto.getEmail())
                .team(team)
                .build();
    }

    public UserDto mapUserToDto(User user) {
        return UserDto
                .builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .teamId(user.getTeam().getId())
                .build();
    }
}
