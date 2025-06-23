package com.example.prj1.board.dto;

import lombok.Data;

@Data
public class BoardForm {
    private Integer id;
    private String title;
    private String content;
    private String writer;
}
// 이거는 값을 담는 용도로 만든 dto
// html 폼에서 입력한 값을 받는 용도