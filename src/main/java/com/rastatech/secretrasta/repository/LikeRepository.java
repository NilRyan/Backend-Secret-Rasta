package com.rastatech.secretrasta.repository;

import com.rastatech.secretrasta.model.LikeEntity;
import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.model.WishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    LikeEntity findByWishAndUser(WishEntity wish, UserEntity user);
}
