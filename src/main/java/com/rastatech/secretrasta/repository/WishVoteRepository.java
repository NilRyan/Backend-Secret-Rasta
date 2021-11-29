package com.rastatech.secretrasta.repository;

import com.rastatech.secretrasta.model.WishVoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishVoteRepository extends JpaRepository<WishVoteEntity, Long> {
}
