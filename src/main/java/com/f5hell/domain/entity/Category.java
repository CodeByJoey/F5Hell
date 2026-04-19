package com.f5hell.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    private String name;
    private Long parentId;
    private String useYn;
    private Integer depth;

    @Builder
    public Category(String name, Long parentId, String useYn, Integer depth) {
        this.name = name;
        this.parentId = parentId;
        this.useYn = useYn;
        this.depth = depth;
    }
}
