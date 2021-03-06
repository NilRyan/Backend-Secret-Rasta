package com.rastatech.secretrasta.repository;

import com.rastatech.secretrasta.model.DonationEntity;
import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.model.WishEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<DonationEntity, Long> {
    List<DonationEntity> findByWish(WishEntity wish);
    List<DonationEntity> findByUser(UserEntity user);

    List<DonationEntity> findByUser_Username(String username);
    List<DonationEntity> findByWish_User_Username(String username);
    List<DonationEntity> findByWish_WishId(Long wishId, Pageable pageable);
}
