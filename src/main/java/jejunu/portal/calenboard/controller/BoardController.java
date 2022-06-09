package jejunu.portal.calenboard.controller;

import jejunu.portal.calenboard.dto.BoardDTO;
import jejunu.portal.calenboard.entity.Board;
import jejunu.portal.calenboard.repository.BoardRepository;
import jejunu.portal.calenboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;

    @PostMapping("/post")
    public Long create(@RequestBody BoardDTO requestDto){
        return boardService.create(requestDto);
    }

    @PutMapping("/update")
    public Long updateBoard(@RequestBody BoardDTO boardDto){
        return boardService.update(boardDto);
    }

    @GetMapping("/board/getList")
    public List<Board> getlist(){
        return boardRepository.findAll();
    }

    @GetMapping("/board/{bid}")
    public Optional<Board> get(@PathVariable Long bid){
        return boardRepository.findById(bid);
    }


    @DeleteMapping("/delete/{bid}")
    public Long delete(@PathVariable Long bid){
        boardRepository.deleteById(bid);
        return bid;
    }

    @GetMapping("/board")
    public Optional<Board> getByDate(LocalDate date){
        return boardService.findByDate(date);
    }

}
