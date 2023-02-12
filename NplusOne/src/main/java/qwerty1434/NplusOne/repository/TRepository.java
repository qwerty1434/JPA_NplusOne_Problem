package qwerty1434.NplusOne.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qwerty1434.NplusOne.domain.Team;

public interface TRepository extends JpaRepository<Team,Long> {
}
