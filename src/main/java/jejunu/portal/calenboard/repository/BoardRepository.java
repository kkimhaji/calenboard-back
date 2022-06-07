package jejunu.portal.calenboard.repository;

import jejunu.portal.calenboard.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByDate(LocalDate date);
}
