package com.rastatech.secretrasta.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "donations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonationEntity {
}
