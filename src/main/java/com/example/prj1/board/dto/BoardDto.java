package com.example.prj1.board.dto;

import com.example.prj1.board.entity.Board;
import com.example.prj1.member.dto.MemberDto;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Board}
 */
//@Value
@Data // 이거는 그냥 원하는 거 쓰면 된대여
public class BoardDto implements Serializable {
    Integer id;
    String title;
    String content;
    MemberDto writer;
    LocalDateTime createdAt;
}
// 이거는 목록보기 말고 하나보기 할 때 쓴거네용