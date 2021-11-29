package com.rastatech.secretrasta.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "wish_votes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishVoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;
    private VoteType voteType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Long wishId;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

}
