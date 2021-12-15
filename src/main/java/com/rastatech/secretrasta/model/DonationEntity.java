package com.rastatech.secretrasta.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "donations")
@Data
@Builder
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

    @Transient
    private String donatorFullName;

    @Transient
    private String donatorUsername;

    public String getDonatorFullName() {
        return this.user.getFirstName() + " " + this.user.getLastName();
    }

    public String getDonatorUsername() {
        return this.user.getUsername();
    }
}
