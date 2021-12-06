package com.rastatech.secretrasta.repository;

import com.rastatech.secretrasta.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    List<UserEntity> findAll();
    Optional<UserEntity> findByUsername(String username);
}
