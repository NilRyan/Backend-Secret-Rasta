package com.rastatech.secretrasta.repository;

import com.rastatech.secretrasta.model.WishEntity;
import com.rastatech.secretrasta.model.WishVoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishVoteRepository extends JpaRepository<WishVoteEntity, Long> {
    List<WishVoteEntity> findAllByWish(WishEntity wish);
}
