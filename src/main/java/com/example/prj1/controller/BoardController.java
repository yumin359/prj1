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

    // create html 보기
    @GetMapping("write")
    public String write() {

        return "board/write";
    }

    // create 실제로 되는 부분
    @PostMapping("write")
    public String writePost(BoardForm data) {

        boardService.add(data);

        return "redirect:/board/list"; // 작성하고 list html로 넘어가게 함
    }

    // CRUD-R-List(목록보기)
    @GetMapping("list")
    public String list(@RequestParam(defaultValue = "1") Integer page,
                       Model model) {

        var result = boardService.list(page);

//        model.addAttribute("boardList", result);
        model.addAllAttributes(result);

        return "board/list";
    }

    // CRUD-R-One(게시물 클릭하면 거기로 이동해서 하나 보기)
    @GetMapping("view")
    public String view(Integer id, Model model) {

        // service에게 일 시키고
        var dto = boardService.get(id);

        // model에 넣고
        model.addAttribute("board", dto);

        // view로 forward
        return "board/view";
    }

    // create, read 했고
    // update, delete는 다음 시간에 하고
    // 남은 시간에는 꾸미는 거 한대용

}
