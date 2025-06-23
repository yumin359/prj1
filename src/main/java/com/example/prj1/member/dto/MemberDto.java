package com.example.prj1.member.dto;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.prj1.member.entity.Member}
 */
//@Value
@Data
public class MemberDto implements Serializable {
    String id;
    String nickName;
    String info;
}