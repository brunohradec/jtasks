package me.bhradec.jtasks.repository;

import me.bhradec.jtasks.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByEmail(String email);

    Optional<User> findFirstByUsername(String username);

    List<User> findAllByTeamId(Long teamId);
}
