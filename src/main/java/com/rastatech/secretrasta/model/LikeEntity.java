package com.rastatech.secretrasta.model;

import javax.persistence.*;


public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    private boolean like;

    @ManyToOne(fetch = FetchType.LAZY)
    private WishEntity wish;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;
}
