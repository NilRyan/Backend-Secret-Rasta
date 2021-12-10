package com.rastatech.secretrasta.repository;

import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.model.WishEntity;
import com.rastatech.secretrasta.model.WishVoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishVoteRepository extends JpaRepository<WishVoteEntity, Long> {
    List<WishVoteEntity> findByWish(WishEntity wish);
    Optional<WishVoteEntity> findByWishAndUser(WishEntity wish, UserEntity user);
}
