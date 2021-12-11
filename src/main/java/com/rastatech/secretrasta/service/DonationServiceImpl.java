package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.request.DonationRequest;
import com.rastatech.secretrasta.model.DonationEntity;
import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.model.WishEntity;
import com.rastatech.secretrasta.repository.DonationRepository;
import com.rastatech.secretrasta.repository.UserRepository;
import com.rastatech.secretrasta.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;
    private final WishRepository wishRepository;
    private final UserRepository userRepository;

    /*
        TODO
            1. Donation should be from currently logged in user/principal
            2. User must be able to donate to a wish
            3. If donation amount exceeds required then it just fills it
            4. If donation exceeds user balance throw error

     */
    @Override
    @Transactional
    public void createDonation(Long wishId, String username, DonationRequest donation) {
        WishEntity wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        UserEntity toUser = wish.getUser();
        UserEntity fromUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (toUser.equals(fromUser)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (wish.getRastagemsRequired() == wish.getRastagemsDonated()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        int rastaGemsRequired = wish.getRastagemsRequired();
        int currentRastaGems = wish.getRastagemsDonated();
        int donationAmount = donation.getAmount();
        DonationEntity newDonation = DonationEntity.builder()
                .user(fromUser)
                .wish(wish)
                .build();
        if ((currentRastaGems + donationAmount) > rastaGemsRequired) {
            donationAmount = rastaGemsRequired - currentRastaGems;
        }
        toUser.addBalance(donationAmount);
        fromUser.decreaseBalance(donationAmount);
        wish.setRastagemsDonated(currentRastaGems + donationAmount);
        newDonation.setAmount(donationAmount);
        donationRepository.save(newDonation);
    }

    @Override
    public List<DonationEntity> fetchDonationsByUser(String username) {
        return donationRepository.findByUser_Username(username);
    }

    @Override
    public List<DonationEntity> fetchReceivedDonationsByUser(String username) {
        return donationRepository.findByWish_User_Username(username);
    }

//    @Override
//    public int fetchDonationsByWish(Long wishId) {
//        WishEntity wish = wishRepository.findById(wishId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//        List<DonationEntity> donations = donationRepository.findByWish(wish);
//        return donations.stream().map(DonationEntity::getAmount).mapToInt(Integer::intValue).sum();
//    }
//
//    @Override
//    public int fetchDonationsByUser(Long userId) {
//        UserEntity user = userRepository.findById(userId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//        List<DonationEntity> donations = donationRepository.findByUser(user);
//        return donations.stream().map(DonationEntity::getAmount).mapToInt(Integer::intValue).sum();
//    }
}
