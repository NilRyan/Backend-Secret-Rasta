package com.rastatech.secretrasta.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "send_gems")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendGemsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sendGemId;

    private int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity sendGemFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity sendGemTo;

    @CreationTimestamp
    private LocalDateTime transactionDate;
}
