package jejunu.portal.calenboard.service;

import jejunu.portal.calenboard.dto.BoardDTO;
import jejunu.portal.calenboard.entity.Board;
import jejunu.portal.calenboard.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public Long create(BoardDTO requestDto){
        return boardRepository.save(requestDto.toEntity()).getBid();
    }

    public Long update(BoardDTO requestDto){
        Long bid = requestDto.getBid();
        Board board = boardRepository.findById(bid)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        return boardRepository.save(requestDto.toEntity()).getBid();
    }

    public Optional<Board> findByDate(LocalDate date){
        Long bid = boardRepository.findByDate(date).getBid();
        return boardRepository.findById(bid);
    }

}
