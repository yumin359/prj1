package com.example.prj1.board.repository;

import com.example.prj1.board.dto.BoardListInfo;
import com.example.prj1.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    List<BoardListInfo> findAllBy(); // 이건 그냥 남겨둘게요

    //    List<BoardListInfo> findAllBy(Pageable pageable);
    Page<BoardListInfo> findAllBy(Pageable pageable);
}