package com.rastatech.secretrasta.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "wishes")
@SQLDelete(sql = "UPDATE wishes SET deleted = true WHERE wishes.wish_id=?")
@Where(clause = "deleted=false")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long wishId;
    private String wishName;

    private String imageUrl;
    private String description;
    private int rastagemsRequired;
    private int rastagemsDonated;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "wish", cascade = CascadeType.ALL)
    private List<LikeEntity> likes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "wish")
    private List<DonationEntity> donations;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "wish", cascade = CascadeType.ALL)
    private List<WishVoteEntity> votes;

    @Builder.Default
    private boolean deleted = Boolean.FALSE;

    @Transient
    private boolean isLiked;

    @Transient
    private int upvotes;

    @Transient
    private int downvotes;

    public void setIsLiked(Long userId) {
        isLiked = likes.stream().filter(like -> Objects.equals(like.getUser().getUserId(), userId)).count() == 1;
    }

    public int getUpvotes() {
        return (int) votes.stream().filter(a -> a.getVoteType().equals(VoteType.UPVOTE)).count();
    }

    public int getDownvotes() {
        return  (int) votes.stream().filter(a -> a.getVoteType().equals(VoteType.DOWNVOTE)).count();
    }
}
