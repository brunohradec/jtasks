package me.bhradec.jtasks.mapper;

import me.bhradec.jtasks.domain.Team;
import me.bhradec.jtasks.dto.TeamDto;
import me.bhradec.jtasks.dto.command.TeamCommandDto;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {
    public Team mapCommandToTeam(TeamCommandDto teamCommandDto) {
        return Team
                .builder()
                .name(teamCommandDto.getName())
                .description(teamCommandDto.getDescription())
                .build();
    }

    public TeamDto mapTeamToDto(Team team) {
        return TeamDto
                .builder()
                .id(team.getId())
                .name(team.getName())
                .description(team.getDescription())
                .build();
    }
}
