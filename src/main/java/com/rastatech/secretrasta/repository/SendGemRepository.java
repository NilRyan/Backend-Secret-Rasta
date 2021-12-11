package com.rastatech.secretrasta.repository;

import com.rastatech.secretrasta.model.AddBalanceEntity;
import com.rastatech.secretrasta.model.SendGemsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SendGemRepository extends CrudRepository<SendGemsEntity, Long> {
}
