package com.example.cryptocurrency.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Price")
public class Price {

    @Id
    private int id;

    private String symbol;

    @JsonProperty("price_usd")
    @Column(name = "price")
    private double priceUsd;
}
