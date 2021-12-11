package com.rastatech.secretrasta.repository;

import com.rastatech.secretrasta.model.AddBalanceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddBalanceRepository extends CrudRepository<AddBalanceEntity, Long> {
    List<AddBalanceEntity> findByUser_Username(String username);
}
