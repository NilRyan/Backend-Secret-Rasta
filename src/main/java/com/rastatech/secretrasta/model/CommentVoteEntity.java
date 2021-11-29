package com.rastatech.secretrasta.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "comment_votes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;
    private VoteType voteType;

    @ManyToOne(fetch = FetchType.LAZY)
    private CommentEntity comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

}
