package com.example.prj1.service;

import com.example.prj1.dto.BoardDto;
import com.example.prj1.dto.BoardForm;
import com.example.prj1.dto.BoardListInfo;
import com.example.prj1.entity.Board;
import com.example.prj1.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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

    public Map<String, Object> list(Integer page) {
//        List<Board> list = boardRepository.findAll();
        // 이렇게 하면 본문까지 다 찾아본 거
        // 그래서 projection 으로 필요한 정보만 가져와보겠습니다
//        List<BoardListInfo> boardList = boardRepository
//                .findAllBy(PageRequest.of(page - 1, 10, Sort.by("id").descending()));
        Page<BoardListInfo> boardPage = boardRepository
                .findAllBy(PageRequest.of(page - 1, 10, Sort.by("id").descending()));

        List<BoardListInfo> boardList = boardPage.getContent();

        Integer rightPageNumber = ((page - 1) / 10 + 1) * 10;
        Integer leftPageNumber = rightPageNumber - 9;
        rightPageNumber = Math.min(rightPageNumber, boardPage.getTotalPages());

        var result = Map.of("boardList", boardList,
                "totalElements", boardPage.getTotalElements(),
                "totalPages", boardPage.getTotalPages(),
                "rightPageNumber", rightPageNumber,
                "leftPageNumber", leftPageNumber,
                "currentPage", page);

        return result;
    }


    public BoardDto get(Integer id) {
        Board board = boardRepository.findById(id).get();
        BoardDto dto = new BoardDto();
        dto.setTitle(board.getTitle());
        dto.setContent(board.getContent());
        dto.setWriter(board.getWriter());
        dto.setId(board.getId());
        dto.setCreatedAt(board.getCreatedAt());

        return dto;
    }
}
