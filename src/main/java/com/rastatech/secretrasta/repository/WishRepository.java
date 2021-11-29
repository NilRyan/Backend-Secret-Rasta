package com.rastatech.secretrasta.repository;

import com.rastatech.secretrasta.model.WishEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends CrudRepository<WishEntity, Long> {
}
