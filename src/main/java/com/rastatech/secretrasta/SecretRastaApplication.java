package com.rastatech.secretrasta;

import com.rastatech.secretrasta.dto.request.DonationRequest;
import com.rastatech.secretrasta.dto.request.WishVoteRequest;
import com.rastatech.secretrasta.model.*;
import com.rastatech.secretrasta.repository.UserRepository;
import com.rastatech.secretrasta.repository.WishRepository;
import com.rastatech.secretrasta.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class SecretRastaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecretRastaApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(RoleService roleService,
						  DonationService donationService,
						  LikeService likeService,
						  WishVoteService wishVoteService,
						  PasswordEncoder passwordEncoder,
						  UserRepository userRepository,
						  WishRepository wishRepository) {
		return args -> {
			Role role_user = roleService.saveRole(new Role(1L, "ROLE_USER"));
			roleService.saveRole(new Role(2L, "ROLE_ADMIN"));

			UserEntity user = UserEntity.builder()
					.userId(1L)
					.username("nilpogi")
					.email("nilpogi@gmail.com")
					.firstName("nil")
					.lastName("pogi")
					.phoneNumber("09111111111")
					.password(passwordEncoder.encode("iampogi"))
					.roles(List.of(role_user))
					.createdAt(LocalDateTime.now())
					.rastaGemsBalance(888)
					.build();

			UserEntity user2 = UserEntity.builder()
					.userId(2L)
					.username("philipcalape")
					.email("philipcalape@gmail.com")
					.firstName("nil")
					.lastName("pogi")
					.phoneNumber("09111111112")
					.password(passwordEncoder.encode("nilispogi"))
					.roles(List.of(role_user))
					.createdAt(LocalDateTime.now())
					.rastaGemsBalance(888)
					.build();

			UserEntity user3 = UserEntity.builder()
					.userId(3L)
					.username("Cots")
					.email("Cots@gmail.com")
					.firstName("Cots")
					.lastName("Cots")
					.phoneNumber("09111111113")
					.password(passwordEncoder.encode("123"))
					.roles(List.of(role_user))
					.createdAt(LocalDateTime.now())
					.rastaGemsBalance(888)
					.build();

			userRepository.saveAll(List.of(user, user2, user3));

			WishEntity wish = WishEntity.builder()
					.wishId(1L)
					.wishName("I only want boss Nil for Christmas")
					.description("kahit selfie lang ni boss Nil sapat na")
					.rastagemsRequired(888)
					.user(user)
					.createdAt(LocalDateTime.now())
					.build();

			WishEntity wish2 = WishEntity.builder()
					.wishId(2L)
					.wishName("I only want boss Cots for Christmas")
					.description("kahit selfie lang ni boss Cots sapat na")
					.rastagemsRequired(88)
					.user(user2)
					.createdAt(LocalDateTime.now())
					.build();

			WishEntity wish3 = WishEntity.builder()
					.wishId(3L)
					.wishName("M1 Pro")
					.description("For whole day coding")
					.rastagemsRequired(8)
					.user(user3)
					.createdAt(LocalDateTime.now())
					.build();

			wishRepository.saveAll(List.of(wish, wish2, wish3));

//			donationService.createDonation(wish.getWishId(), "Cots", new DonationRequest(50));
//			donationService.createDonation(wish2.getWishId(), "nilpogi", new DonationRequest(50));
//			donationService.createDonation(wish3.getWishId(), "nilpogi", new DonationRequest(8));
//			donationService.createDonation(wish.getWishId(), "Cots", new DonationRequest(50));

			likeService.like(wish.getWishId(), user.getUserId());
			likeService.like(wish2.getWishId(), user.getUserId());

			wishVoteService.vote(user.getUserId(), wish.getWishId(), new WishVoteRequest(VoteType.UPVOTE));
			wishVoteService.vote(user.getUserId(), wish2.getWishId(), new WishVoteRequest(VoteType.DOWNVOTE));
			wishVoteService.vote(user.getUserId(), wish3.getWishId(), new WishVoteRequest(VoteType.UPVOTE));
			wishVoteService.vote(user2.getUserId(), wish.getWishId(), new WishVoteRequest(VoteType.UPVOTE));
		};
	}
}
