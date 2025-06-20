package com.example.prj1.controller;

import com.example.prj1.dto.BoardForm;
import com.example.prj1.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("write")
    public String write() {

        return "board/write";
    }

    @PostMapping("write")
    public String writePost(BoardForm data) {

        boardService.add(data);

        return "redirect:/board/list";
    }

    // CRUD-R-List(목록보기), One(
    @GetMapping("list")
    public String list(Model model) {

        var result = boardService.list();

        model.addAttribute("boardList", result);

        return "board/list";
    }


}
