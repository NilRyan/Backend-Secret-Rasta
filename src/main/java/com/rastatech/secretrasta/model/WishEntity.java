package com.rastatech.secretrasta.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "wishes")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "wish", cascade = CascadeType.ALL)
    private List<DonationEntity> donations;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "wish", cascade = CascadeType.ALL)
    private List<WishVoteEntity> votes;

    @Transient
    private boolean isLiked;

    @Transient
    private int upvotes;

    @Transient
    private int downvotes;

    public void setIsLiked(Long userId) {
        isLiked = likes.stream().filter(like -> like.getUser().getUserId() == userId).count() == 1 ? true : false;
    }

    public int getUpvotes() {
        return (int) votes.stream().filter(a -> a.getVoteType().equals(VoteType.UPVOTE)).count();
    }

    public int getDownvotes() {
        return  (int) votes.stream().filter(a -> a.getVoteType().equals(VoteType.DOWNVOTE)).count();
    }
}
