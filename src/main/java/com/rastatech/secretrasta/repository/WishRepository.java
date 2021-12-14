package com.rastatech.secretrasta.repository;

import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.model.WishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishRepository extends CrudRepository<WishEntity, Long> {
    List<WishEntity> findByUserAndDeletedFalse(UserEntity user, Pageable pageable);
    List<WishEntity> findByUser_UserId(Long userId);
    List<WishEntity> findByDeletedFalse(Pageable pageable);
    List<WishEntity> findByLikes_User_UserIdAndDeletedFalse(Long userId, Pageable pageable);
    List<WishEntity> findDistinctByDonations_User_UserIdAndDeletedFalse(Long userId, Pageable pageable);
    @Query("SELECT w FROM WishEntity w WHERE w.rastagemsDonated < w.rastagemsRequired AND w.user.userId = ?1")
    List<WishEntity> findActiveWishesByUserId(Long userId, Pageable pageable);

    @Query("SELECT w FROM WishEntity w WHERE w.rastagemsDonated = w.rastagemsRequired AND w.user.userId = ?1")
    List<WishEntity> findFulfilledWishesByUserId(Long userId, Pageable pageable);

}
