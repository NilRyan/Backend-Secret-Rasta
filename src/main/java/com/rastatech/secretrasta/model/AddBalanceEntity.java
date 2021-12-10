package com.rastatech.secretrasta.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "add_balance")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddBalanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long addBalanceId;

    private int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @CreationTimestamp
    private LocalDateTime transactionDate;
}
