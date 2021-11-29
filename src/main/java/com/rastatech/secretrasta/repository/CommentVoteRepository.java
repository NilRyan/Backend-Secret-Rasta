package com.rastatech.secretrasta.repository;

import com.rastatech.secretrasta.model.CommentVoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentVoteRepository extends JpaRepository<CommentVoteEntity, Long> {
}
