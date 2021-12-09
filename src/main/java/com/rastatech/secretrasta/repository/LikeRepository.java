package com.rastatech.secretrasta.repository;

import com.rastatech.secretrasta.model.LikeEntity;
import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.model.WishEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByWishAndUser(WishEntity wish, UserEntity user);
    boolean existsByWishAndUser(WishEntity wish, UserEntity user);
    List<LikeEntity> findByUser(UserEntity user);
}
