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
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberService memberService;
    private final PhotoService photoService;

    @Transactional
    public Long create(BoardDTO boardDTO, HttpServletRequest request, MultipartFile[] uploadFiles) throws Exception {
        Member loginMem = memberService.getLoginUser(request);
        changeImgSrc(boardDTO, uploadFiles, loginMem);
        return boardRepository.save(boardDTO.toEntity(loginMem)).getBid();
    }

    private void changeImgSrc(BoardDTO boardDTO, MultipartFile[] uploadFiles, Member loginMem) throws FileNotFoundException {
        List<String> photoName =  photoService.uploadFile(uploadFiles, loginMem.getUid(), boardDTO.getDate());
        List<String> changeimgsrc = new ArrayList<>();
        String content = boardDTO.getContent();
        Pattern imgsrc = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");
        Matcher matcher = imgsrc.matcher(content);
        while(matcher.find()){
            String src = matcher.group();
            if(src==null || src.length() ==0) continue;
            changeimgsrc.add(src);
        }

        for(int i=0;i<changeimgsrc.size();i++){
            content = content.replace(changeimgsrc.get(i), "<img src=\""+photoName.get(i)+"\">");
        }
        boardDTO.setContent(content);
    }

    public Long update(BoardDTO boardDTO, HttpServletRequest request, MultipartFile[] uploadFiles) throws FileNotFoundException {
        Long bid = boardDTO.getBid();
        Member loginMem = memberService.getLoginUser(request);
        Board board = boardRepository.findById(bid)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        photoService.deleteFile(loginMem.getUid(), board.getDate());
        changeImgSrc(boardDTO, uploadFiles, loginMem);
        return boardRepository.save(boardDTO.toEntity(loginMem)).getBid();
    }

    public Optional<Board> findByDate(String nowDate, HttpServletRequest request){
        Member loginM = memberService.getLoginUser(request);
        return boardRepository.findByDateAndMember(nowDate,loginM);
    }

    public BoardWithPhoto get(String nowDate, HttpServletRequest request){
        Member loginM = memberService.getLoginUser(request);
        Long uid = loginM.getUid();
        Optional<Board> b = boardRepository.findByDateAndMember(nowDate,loginM);
        String[] result = photoService.getlist(uid, b.get().getDate());
        return BoardWithPhoto.builder().board(b).photolist(result).build();
    }

    public List<Board> getlistAll(HttpServletRequest request){
        Member loginM = memberService.getLoginUser(request);
        return boardRepository.findAllByMember(loginM);
    }

    public boolean postexist(String nowDate, HttpServletRequest request){
        Optional<Board> result = findByDate(nowDate, request);
        return result.isPresent();
    }

    public Long delete(Long bid){
        Board b = boardRepository.findById(bid).get();
        Long uid = b.getMember().getUid();
        String date = b.getDate();
        photoService.deleteFile(uid, date);
        boardRepository.delete(b);
        return bid;
    }

    public Map<String, String> getThumnailsMonthly(HttpServletRequest request, String nowYearMon) {
        Map<String, String> monthlyMap = new TreeMap<>();
        Member loginUser = memberService.getLoginUser(request);
        List<Board> result = boardRepository.findAllByMember(loginUser);
        String nextDate = getNextDate(nowYearMon);
        for (Board b : result) {
            String date = b.getDate();
            if (date.compareTo(nowYearMon) >= 0 && date.compareTo(nextDate) < 0) {
                String day = date.substring(8,10);
                Long uid = loginUser.getUid();
//                String totalDate = nowYearMon+day;
                String[] dir = photoService.getlist(uid, date);
                if(dir!=null){
                    String src = "http://localhost:8082/static/photo/"+uid.toString()+"/"+date+"/"+dir[0];
                    monthlyMap.put(day, src);
                }
            }
        }
        return monthlyMap;
    }


    public String getNextDate(String nowDate) {
        String year = nowDate.substring(0, 5);
        int month = Integer.parseInt(nowDate.substring(5));
        month++;
        String nextDate = "";
        switch (month) {
            case 13:
                month = 1;
                int nextyear = Integer.parseInt(year.substring(0, 4)) + 1;
                year = Integer.toString(nextyear) + "-";
            default:
                nextDate = year + "0" + Integer.toString(month);
                break;
            case 12:
            case 11:
                nextDate = year + Integer.toString(month);
                break;
        }
        return nextDate;
    }


}
