package com.rastatech.secretrasta.repository;

import com.rastatech.secretrasta.model.CommentEntity;
import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.model.WishEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<CommentEntity, Long> {
    List<CommentEntity> findByWishEntity(WishEntity entity);
    List<CommentEntity> findByUserEntity(UserEntity user);
}
