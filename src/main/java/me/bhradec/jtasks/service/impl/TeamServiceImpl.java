package me.bhradec.jtasks.service.impl;

import me.bhradec.jtasks.domain.Team;
import me.bhradec.jtasks.dto.TeamDto;
import me.bhradec.jtasks.dto.command.TeamCommandDto;
import me.bhradec.jtasks.exception.ConflictException;
import me.bhradec.jtasks.exception.NotFoundException;
import me.bhradec.jtasks.mapper.TeamMapper;
import me.bhradec.jtasks.repository.TeamRepository;
import me.bhradec.jtasks.service.TeamService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    public TeamServiceImpl(TeamRepository teamRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    @Override
    public TeamDto save(TeamCommandDto teamCommandDto) throws ConflictException {
        Team team = teamMapper.mapCommandToTeam(teamCommandDto);

        teamRepository
                .findFirstByName(teamCommandDto.getName())
                .orElseThrow(() -> new ConflictException("Team with the provided name already exists."));

        return teamMapper.mapTeamToDto(teamRepository.save(team));
    }

    @Override
    public List<TeamDto> findAll() {
        return teamRepository
                .findAll()
                .stream()
                .map(teamMapper::mapTeamToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TeamDto> findById(Long teamId) {
        return teamRepository
                .findById(teamId)
                .map(teamMapper::mapTeamToDto);
    }

    @Override
    public TeamDto updateById(Long teamId, TeamCommandDto teamCommandDto) throws NotFoundException, ConflictException {
        Team updatedTeam = teamMapper.mapCommandToTeam(teamCommandDto);

        Team team = teamRepository
                .findById(teamId)
                .orElseThrow(() -> new NotFoundException("Team with the provided id does not exist."));

        updatedTeam.setId(team.getId());

        if (!team.getName().equals(updatedTeam.getName())
                && teamRepository.findFirstByName(team.getName()).isPresent()) {
            throw new ConflictException("Team with the provided name already exists.");
        }

        return teamMapper.mapTeamToDto(teamRepository.save(updatedTeam));
    }

    @Override
    public void deleteById(Long teamId) throws NotFoundException {
        teamRepository
                .findById((teamId))
                .orElseThrow(() -> new NotFoundException("Team with the provided id does not exist."));

        teamRepository.deleteById(teamId);
    }
}
