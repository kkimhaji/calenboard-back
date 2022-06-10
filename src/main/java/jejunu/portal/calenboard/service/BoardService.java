package jejunu.portal.calenboard.service;

import jejunu.portal.calenboard.dto.BoardDTO;
import jejunu.portal.calenboard.entity.Board;
import jejunu.portal.calenboard.entity.Member;
import jejunu.portal.calenboard.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.nio.channels.MembershipKey;
import java.time.LocalDate;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberService memberService;

    @Transactional
    public Long create(BoardDTO requestDto, HttpServletRequest request){
        Member loginMem = memberService.getLoginUser(request);
        return boardRepository.save(requestDto.toEntity(loginMem)).getBid();
    }

    public Long update(BoardDTO requestDto, HttpServletRequest request){
        Long bid = requestDto.getBid();
        Member loginMem = memberService.getLoginUser(request);
        Board board = boardRepository.findById(bid)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        return boardRepository.save(requestDto.toEntity(loginMem)).getBid();
    }

    public Board findByDate(LocalDate date){
        Long bid = boardRepository.findByDate(date).getBid();
        Optional<Board> optionalBoard = boardRepository.findById(bid);
        return optionalBoard.orElse(null);
    }

}
