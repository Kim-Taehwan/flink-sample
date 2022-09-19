package com.example.flink.fraud.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Transaction {
    private long accountId;
    private long timestamp;
    private double amount;
}
