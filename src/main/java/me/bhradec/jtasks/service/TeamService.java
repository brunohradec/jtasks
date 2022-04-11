package me.bhradec.jtasks.service;

import me.bhradec.jtasks.dto.TeamDto;
import me.bhradec.jtasks.dto.command.TeamCommandDto;
import me.bhradec.jtasks.exception.ConflictException;
import me.bhradec.jtasks.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    TeamDto save(TeamCommandDto teamCommandDto) throws ConflictException;

    List<TeamDto> findAll();

    Optional<TeamDto> findById(Long teamId);

    TeamDto updateById(Long teamId, TeamCommandDto teamCommandDto) throws NotFoundException, ConflictException;

    void deleteById(Long teamId) throws NotFoundException;
}
