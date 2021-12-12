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
import java.util.stream.Collectors;

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
    private String wishOwnerFullName;

    @Transient
    private String wishOwnerUsername;

    @Transient
    private String voteStatus;

    @Transient
    private boolean isLiked;

    @Transient
    private int upvotes;

    @Transient
    private int downvotes;


    public String getWishOwnerUsername() {
        return this.user.getUsername();
    }

    public String getWishOwnerFullName() {
        return this.user.getFirstName() + " " + this.user.getLastName();
    }

    public void setIsLiked(Long userId) {
        isLiked = likes.stream().filter(like -> Objects.equals(like.getUser().getUserId(), userId)).count() == 1;
    }

    public void setVoteStatus(Long userId) {
        List<WishVoteEntity> v = votes.stream().filter( vote -> Objects.equals(vote.getUser().getUserId(), userId)).collect(Collectors.toList());

        if (v.size() == 0) {
            voteStatus = "NONE";
        } else if (v.size() == 1) {
            voteStatus = v.get(0).getVoteType().name();
        }
    }

    public int getUpvotes() {
        return (int) votes.stream().filter(a -> a.getVoteType().equals(VoteType.UPVOTE)).count();
    }

    public int getDownvotes() {
        return  (int) votes.stream().filter(a -> a.getVoteType().equals(VoteType.DOWNVOTE)).count();
    }
}
