package jejunu.portal.calenboard.repository;

import jejunu.portal.calenboard.entity.Board;
import jejunu.portal.calenboard.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByDate(LocalDate date);

    List<Board> findAllByMember(Member member);
}
