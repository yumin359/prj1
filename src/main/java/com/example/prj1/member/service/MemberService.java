package com.example.prj1.member.service;

import com.example.prj1.member.dto.MemberForm;
import com.example.prj1.member.entity.Member;
import com.example.prj1.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void add(MemberForm data) {

        Optional<Member> db = memberRepository.findById(data.getId());
        if (db.isEmpty()) {
            // 새 Entity 객체 생성해서
            Member member = new Member();

            // data에 있는 것 entity에 옮겨 담고
            member.setId(data.getId());
            member.setPassword(data.getPassword());
            member.setNickName(data.getNickName());
            member.setInfo(data.getInfo());

            // repository.save()
            memberRepository.save(member);
        } else {
            throw new DuplicateKeyException(data.getId() + "는 이미 있는 아이디입니다.");
        }


    }


}
