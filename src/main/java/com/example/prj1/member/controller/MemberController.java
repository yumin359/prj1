package com.example.prj1.member.controller;

import com.example.prj1.member.dto.MemberDto;
import com.example.prj1.member.dto.MemberForm;
import com.example.prj1.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
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
    public String view(String id,
                       @SessionAttribute(value = "loggedInUser", required = false)
                       MemberDto user,
                       Model model,
                       RedirectAttributes rttr) {
        MemberDto member = memberService.get(id);

        if (user != null) {
            if (member.getId().equals(user.getId())) {
                model.addAttribute("member", member);
                return "member/view";
            }
        }
        rttr.addFlashAttribute("alert",
                Map.of("code", "warning", "message", "권한이 없습니다."));
        return "redirect:/board/list";
    }

    @PostMapping("remove")
    public String remove(MemberForm data, RedirectAttributes rttr) {
        boolean result = memberService.remove(data);

        if (result) {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "danger", "message", data.getId() + "님 탈퇴되었습니다."));
            return "redirect:/board/list";
        } else {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "danger", "message", "암호가 일치하지 않습니다."));

            rttr.addAttribute("id", data.getId());

            return "redirect:/member/view";
        }
    }

    @GetMapping("edit")
    public String edit(String id, Model model) {
        model.addAttribute("member", memberService.get(id));
        return "member/edit";
    }

    @PostMapping("edit")
    public String edit(MemberForm data, RedirectAttributes rttr) {
        boolean result = memberService.update(data);

        if (result) {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "success", "message", "회원 정보가 변경되었습니다."));
            rttr.addAttribute("id", data.getId());
            return "redirect:/member/view";
        } else {
            rttr.addAttribute("id", data.getId());

            rttr.addFlashAttribute("alert",
                    Map.of("code", "warning", "message", "암호가 일치하지 않습니다."));
            return "redirect:/member/edit";
        }
    }

    @PostMapping("changePw")
    public String changePassword(String id,
                                 String oldPassword,
                                 String newPassword,
                                 RedirectAttributes rttr) {

        boolean result = memberService.updatePassword(id, oldPassword, newPassword);
        if (result) {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "success", "message", "암호가 변경되었습니다."));
        } else {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "warning", "message", "암호가 일치하지 않습니다."));

        }

        rttr.addAttribute("id", id);
        return "redirect:/member/edit";
    }

    @GetMapping("login")
    public String loginForm() {
        return "member/login";
    }

    @PostMapping("login")
    public String loginProcess(String id, String password,
                               HttpSession session,
                               RedirectAttributes rttr) {

        boolean result = memberService.login(id, password, session);

        if (result) {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "success", "message", "로그인 되었습니다."));
            // 로그인 성공
            return "redirect:/board/list";
        } else {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "warning", "message", "아이디/패스워드가 일치하지 않습니다."));
            rttr.addFlashAttribute("id", id);

            // 로그인 실패
            return "redirect:/member/login";
        }

    }

    // @GetMapping("logout") // 아마 이거로 쓰려는 것 같다는데 gpt는
    @RequestMapping("logout")
    public String logout(HttpSession session, RedirectAttributes rttr) {
        session.invalidate();
        // 로그인 정보 및 세션에 저장된 모든 데이터를 삭제하고, 세션 자체를 무효화(종료) 시킴
        // 그래서 로그아웃이나 회원 탈퇴 같은 "사용자 상태 초기화" 시점에만 쓰는 게 좋대
        rttr.addFlashAttribute("alert",
                Map.of("code", "success", "message", "로그아웃 되었습니다."));
        return "redirect:/board/list";
    }

}
