package jejunu.portal.calenboard.service;

import jejunu.portal.calenboard.dto.BoardDTO;
import jejunu.portal.calenboard.entity.Board;
import jejunu.portal.calenboard.entity.Member;
import jejunu.portal.calenboard.model.BoardWithPhoto;
import jejunu.portal.calenboard.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@AllArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberService memberService;
    private final PhotoService photoService;

    @Transactional
    public Long create(BoardDTO boardDTO, HttpServletRequest request, MultipartFile[] uploadFiles) throws Exception {
        Member loginMem = memberService.getLoginUser(request);
        photoService.uploadFile(uploadFiles, loginMem.getUid(), boardDTO.getDate());
        return boardRepository.save(boardDTO.toEntity(loginMem)).getBid();
    }

    public Long update(BoardDTO boardDTO, HttpServletRequest request, MultipartFile[] uploadFiles){
        Long bid = boardDTO.getBid();
        Member loginMem = memberService.getLoginUser(request);
        Board board = boardRepository.findById(bid)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        photoService.updateFile(uploadFiles, loginMem.getUid(), board.getDate());
        return boardRepository.save(boardDTO.toEntity(loginMem)).getBid();
    }

    public Optional<Board> findByDate(String nowDate, HttpServletRequest request){
        Member loginM = memberService.getLoginUser(request);
        Optional<Board> result = boardRepository.findByDateAndMember(nowDate,loginM);
        return result;
    }

    public BoardWithPhoto get(String nowDate, HttpServletRequest request){
        Member loginM = memberService.getLoginUser(request);
        Long uid = loginM.getUid();
        Optional<Board> b = boardRepository.findByDateAndMember(nowDate,loginM);
        String[] result = photoService.getlist(uid, b.get().getDate());
        BoardWithPhoto bwp = BoardWithPhoto.builder().board(b).photolist(result).build();
        return bwp;
    }

    public List<Board> getlistAll(HttpServletRequest request){
        Member loginM = memberService.getLoginUser(request);
        return boardRepository.findAllByMember(loginM);
    }

    public boolean postexist(String nowDate, HttpServletRequest request){
        Optional<Board> result = findByDate(nowDate, request);
        return result.isPresent();
    }


}
