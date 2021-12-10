package com.rastatech.secretrasta.repository;

import com.rastatech.secretrasta.model.AddBalanceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddBalanceRepository extends CrudRepository<AddBalanceEntity, Long> {
}
