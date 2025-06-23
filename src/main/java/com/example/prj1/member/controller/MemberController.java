package com.example.prj1.member.controller;

import com.example.prj1.member.dto.MemberForm;
import com.example.prj1.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("signup")
    public String signForm() {
        return "member/signup";
    }

    @PostMapping("signup")
    public String signup(MemberForm data, RedirectAttributes rttr) {
        try {// service
            memberService.add(data);

            rttr.addFlashAttribute("alert",
                    Map.of("code", "success", "message", "회원 가입되었습니다."));

            return "redirect:/board/list";
        } catch (DuplicateKeyException e) {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "warning", "message", e.getMessage()));

            rttr.addFlashAttribute("member", data); // 쓴 거 안 사라지게 하는 거

            return "redirect:/member/signup";
        }
    }

    @GetMapping("list")
    public String list(Model model) {
        model.addAttribute("memberList", memberService.list());
        return "member/list";
    }

    // 여까지가 회원 C
    // 나머지 회원 RUD 해봅시당 R은 하나보기 하면 됨
    @GetMapping("view")
    public String view(String id, Model model) {
        var dto = memberService.get(id);
        model.addAttribute("member", dto);
        return "member/view";
    }

}
