package com.rastatech.secretrasta.repository;

import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.model.WishEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishRepository extends CrudRepository<WishEntity, Long> {
    List<WishEntity> findAllByUserEntity(UserEntity user);
}
