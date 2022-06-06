package jejunu.portal.calenboard.controller;

import jejunu.portal.calenboard.dto.BoardDto;
import jejunu.portal.calenboard.entity.Board;
import jejunu.portal.calenboard.repository.BoardRepository;
import jejunu.portal.calenboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;

    @PostMapping("/post")
    public Long create(@RequestBody BoardDto requestDto){
        return boardService.create(requestDto);
    }

    @PutMapping("/update")
    public Long updateBoard(@RequestBody BoardDto boardDto){
        return boardService.update(boardDto);
    }

    @GetMapping("/board/{id}")
    public Optional<Board> getlist(@PathVariable Long bid){
        return boardRepository.findById(bid);
    }



}
