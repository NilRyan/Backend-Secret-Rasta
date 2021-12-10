package com.rastatech.secretrasta.repository;

import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.model.WishEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishRepository extends CrudRepository<WishEntity, Long> {
    List<WishEntity> findAllByUser(UserEntity user, Pageable pageable);
    List<WishEntity> findAll(Pageable pageable);
    List<WishEntity> findByLikes_User(UserEntity user, Pageable pageable);
    List<WishEntity> findByDonations_User_UserId(Long userId, Pageable pageable);
}
