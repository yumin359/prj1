package com.example.prj1.service;

import com.example.prj1.dto.BoardForm;
import com.example.prj1.dto.BoardListInfo;
import com.example.prj1.entity.Board;
import com.example.prj1.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    public void add(BoardForm formData) {
        Board board = new Board();
        board.setTitle(formData.getTitle());
        board.setContent(formData.getContent());
        board.setWriter(formData.getWriter());

        boardRepository.save(board);
    }

    public List<BoardListInfo> list(Integer page) {
//        List<Board> list = boardRepository.findAll();
        // 이렇게 하면 본문까지 다 찾아본 거
        // 그래서 projection 으로 필요한 정보만 가져와보겠습니다
        List<BoardListInfo> boardList = boardRepository
                .findAllBy(PageRequest.of(page - 1, 10, Sort.by("id")));

        return boardList;
    }

    // 전체 개수 받아오기
    public int getLastPage(Integer page) {
        long count = boardRepository.count(); // 전체 페이지 개수
        return (int) (count - 1) / 10 + 1;
//        return (int) Math.ceil(count / 10.0); // 총 페이지 수(마지막 페이지) 계산
    }

    public int getRightPage(Integer page) {
        return ((page - 1) / 10 + 1) * 10;
    }

    public int getLeftPage(Integer page) {
        return getRightPage(page) - 9;
    }

    public int prevPage(Integer page) {
        return getLeftPage(page) - 10;
    }

    public int nextPage(Integer page) {
        return getRightPage(page) + 1;
    }

    // keyword 가져오기


    // 이게 10개씩 데이터 dto로 가져오는 거

    /*
    public void page(Integer page) {
        List<BoardListInfo> list = boardRepository
                .findAllBy(PageRequest.of(page - 1, 10, Sort.by("id")));
        for (BoardListInfo boardListInfo : list) {
            System.out.print(boardListInfo.getId() + " | ");
            System.out.print(boardListInfo.getTitle() + " | ");
            System.out.print(boardListInfo.getWriter() + " | ");
            System.out.print(boardListInfo.getCreatedAt());
            System.out.println();
        }
    }
    // 화면 말고 콘솔창에 10개씩 출력함
     */


}
