package com.rastatech.secretrasta.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "send_gems")
@Builder
@Data
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
