package com.rastatech.secretrasta.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "votes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;
    private VoteType voteType;

    @ManyToOne(fetch = FetchType.LAZY)
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

}
