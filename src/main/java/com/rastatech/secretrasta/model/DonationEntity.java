package com.rastatech.secretrasta.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "donations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long donationId;

    private int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    private WishEntity wish;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @CreationTimestamp
    private LocalDateTime transactionDate;
}