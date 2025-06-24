package com.example.prj1.board.dto;

import com.example.prj1.member.dto.MemberListInfo;

import java.time.LocalDateTime;

public interface BoardListInfo {
    public Integer getId();

    public String getTitle();

    // public String getWriter();
    public MemberListInfo getWriter();

    public LocalDateTime getCreatedAt();
    // public은 생략 가능
}
