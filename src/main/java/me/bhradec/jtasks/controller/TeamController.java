package me.bhradec.jtasks.controller;

import me.bhradec.jtasks.dto.TeamDto;
import me.bhradec.jtasks.dto.command.TeamCommandDto;
import me.bhradec.jtasks.exception.ConflictException;
import me.bhradec.jtasks.exception.NotFoundException;
import me.bhradec.jtasks.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/teams")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    public ResponseEntity<TeamDto> save(@Valid @RequestBody TeamCommandDto teamCommandDto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(teamService.save(teamCommandDto));
        } catch (ConflictException exception) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    exception.getMessage()
            );
        }
    }

    @GetMapping
    public ResponseEntity<List<TeamDto>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(teamService.findAll());
    }

    @GetMapping("{teamId}")
    public ResponseEntity<TeamDto> findById(@PathVariable Long teamId) {
        return teamService
                .findById(teamId)
                .map(teamDto -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(teamDto))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Team with the provided id not found."
                ));
    }

    @PutMapping("{teamId}")
    public ResponseEntity<TeamDto> updateById(
            @PathVariable Long teamId,
            @Valid @RequestBody TeamCommandDto teamCommandDto) {

        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(teamService.updateById(teamId, teamCommandDto));
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

    @DeleteMapping("{teamId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long teamId) {
        try {
            teamService.deleteById(teamId);
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
