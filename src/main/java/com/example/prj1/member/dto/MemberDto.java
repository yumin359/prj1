package com.example.prj1.member.dto;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.prj1.member.entity.Member}
 */
@Data
public class MemberDto implements Serializable {
    private String id;
    private String nickName;
    private String info;
    private LocalDateTime createdAt;
}