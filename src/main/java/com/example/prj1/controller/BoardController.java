package com.example.prj1.controller;

import com.example.prj1.dto.BoardForm;
import com.example.prj1.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "") String keyword,
                       Model model) {

        var result = boardService.list(page); // 나는 128page가 마지막 페이지
        int lastPage = boardService.getLastPage(page);
        int rightPage = boardService.getRightPage(page);
        int leftPage = boardService.getLeftPage(page);
        int prevPage = boardService.prevPage(page);
        int nextPage = boardService.nextPage(page);
        rightPage = Math.min(rightPage, lastPage);

        model.addAttribute("lastPage", lastPage);
        model.addAttribute("leftPage", leftPage);
        model.addAttribute("rightPage", rightPage);
        model.addAttribute("prevPage", prevPage);
        model.addAttribute("nextPage", nextPage);

//        int offset = (page - 1) * 10;


        model.addAttribute("boardList", result); // 첫번째 페이지 10개 바로 출력
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("page", page);

        return "board/list";
    }


//    @GetMapping("list")
//    public String list(@RequestParam(defaultValue = "1") Integer page) {
//        boardService.page(page);
//        return "board/list";
//    }


}
