package com.example.prj1.member;

import com.example.prj1.member.dto.MemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

    @GetMapping("signup")
    public String signForm() {
        return "member/signup";
    }

    @PostMapping("signup")
    public String signup(MemberForm data, RedirectAttributes rttr) {
        // service
        System.out.println(data);

        rttr.addFlashAttribute("alert",
                Map.of("code", "success", "message", "회원 가입되었습니다."));

        return "redirect:/board/list";
    }
}
