package com.example.cryptocurrency.dto;

import lombok.Data;

@Data
public class PriceDTO {
    private String symbol;
    private double priceUsd;
}
