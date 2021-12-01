package com.rastatech.secretrasta.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "wish_likes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    private WishEntity wish;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;
}
