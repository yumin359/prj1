package com.example.prj1.board.controller;

import com.example.prj1.board.dto.BoardForm;
import com.example.prj1.board.service.BoardService;
import com.example.prj1.member.dto.MemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {

    private final BoardService boardService;

    // create html 보기
    @GetMapping("write")
    public String writeForm(HttpSession session, RedirectAttributes rttr) {

        Object user = session.getAttribute("loggedInUser");

        if (user != null) {
            // 로그인 되었을 때
            return "board/write";
        } else {
            // 로그인 안 됐을 때
            rttr.addFlashAttribute("alert",
                    Map.of("code", "warning", "message", "로그인 후 글을 작성해주세요."));

            return "redirect:/member/login";
        }
    }

    // create 실제로 되는 부분
    @PostMapping("write")
    public String writePost(BoardForm data,
//                            HttpSession session,
                            @SessionAttribute(name = "loggedInUser", required = false) MemberDto user,
                            RedirectAttributes rttr) {
//        Object user = session.getAttribute("loggedUser");
        if (user != null) {
//            MemberDto dto = (MemberDto) user;
            boardService.add(data, user);

            rttr.addFlashAttribute("alert",
                    Map.of("code", "primary", "message", "새 게시물이 등록되었습니다."));

            return "redirect:/board/list"; // 작성하고 list html로 넘어가게 함
        } else {
            return "redirect:/member/login";
        }

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

    @PostMapping("remove")
    public String remove(Integer id,
                         @SessionAttribute(value = "loggedInUser", required = false)
                         MemberDto user,
                         RedirectAttributes redirectAttributes) {
        boolean result = boardService.remove(id, user);

        if (result) {
            redirectAttributes.addFlashAttribute("alert",
                    Map.of("code", "danger", "message", id + "번 게시물이 삭제 되었습니다."));

            return "redirect:/board/list";
        } else {
            redirectAttributes.addFlashAttribute("alert",
                    Map.of("code", "danger", "message", id + "번 게시물이 삭제 되지 않았습니다."));
            redirectAttributes.addAttribute("id", id);
            return "redirect:/board/view";
        }
    }

    @GetMapping("edit")
    public String edit(Integer id, Model model) {
        var dto = boardService.get(id);
        model.addAttribute("board", dto);
        return "board/edit";
    }

    @PostMapping("edit")
    public String editPost(BoardForm data, RedirectAttributes rttr) {
        boardService.update(data);

        rttr.addFlashAttribute("alert",
                Map.of("code", "success", "message",
                        data.getId() + "번 게시물이 수정되었습니다."));


        rttr.addAttribute("id", data.getId());

        return "redirect:/board/view";
    }


}
