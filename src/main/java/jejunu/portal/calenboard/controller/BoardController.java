package jejunu.portal.calenboard.controller;

import jejunu.portal.calenboard.dto.BoardDTO;
import jejunu.portal.calenboard.entity.Board;
import jejunu.portal.calenboard.repository.BoardRepository;
import jejunu.portal.calenboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;

    @PostMapping("/post")
    public Long create(@RequestBody BoardDTO requestDto, HttpServletRequest request){
        return boardService.create(requestDto, request);
    }

    @PutMapping("/update")
    public Long updateBoard(@RequestBody BoardDTO boardDto, HttpServletRequest request){
        return boardService.update(boardDto, request);
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
    public Board getByDate(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date){
        return boardService.findByDate(date);
    }

}
