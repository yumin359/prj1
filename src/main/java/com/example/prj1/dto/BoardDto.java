package com.example.prj1.dto;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.prj1.entity.Board}
 */
//@Value
@Data // 이거는 그냥 원하는 거 쓰면 된대여
public class BoardDto implements Serializable {
    Integer id;
    String title;
    String content;
    String writer;
    LocalDateTime createdAt;
}
// 이거는 목록보기 말고 하나보기 할 때 쓴거네용