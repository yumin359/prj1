package com.example.prj1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "board") // 생략해도 만들어지긴 한대요
@Setter
@Getter
@ToString
public class Board {
    // 엔티티 만들 때는 테이블 만든다고 생각하고 만들면 돼용
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String content;
    private String writer;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
