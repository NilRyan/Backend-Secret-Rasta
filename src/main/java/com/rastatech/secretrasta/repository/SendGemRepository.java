package com.rastatech.secretrasta.repository;

import com.rastatech.secretrasta.model.AddBalanceEntity;
import com.rastatech.secretrasta.model.SendGemsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SendGemRepository extends CrudRepository<SendGemsEntity, Long> {
    List<SendGemsEntity> findByUser_Username(String userName);
}
