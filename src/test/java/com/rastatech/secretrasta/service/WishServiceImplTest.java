package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.repository.LikeRepository;
import com.rastatech.secretrasta.repository.UserRepository;
import com.rastatech.secretrasta.repository.WishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
class WishServiceImplTest {

    @Mock
    private WishRepository wishRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private LikeRepository likeRepository;
    @Mock
    private WishVoteService wishVoteService;
    @Mock
    private DonationService donationService;
    @Mock
    private ModelMapper modelMapper;
    @Autowired
    private WishService underTest;

    @BeforeEach
    void setUp() {
//        underTest = new WishServiceImpl(wishRepository,
//                userRepository,
//                donationService,
//                likeRepository,
//                wishVoteService,
//                modelMapper);
    }

    @Test
    void fetchWishesGrantedByUser() {
        /*
        * TODO
        *   1. fetchWishes
        *   2. filter wishes that has rastagems current greater than rastagems required
        *   3. filter wishes that has the authenticated user
        *   4. return the list of results
        *   5. QUERY ("SELECT )
        */

        // given


        // when

        // then
    }
}