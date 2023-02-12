package qwerty1434.NplusOne.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qwerty1434.NplusOne.domain.Member;

public interface MRepository extends JpaRepository<Member,Long> {
}
