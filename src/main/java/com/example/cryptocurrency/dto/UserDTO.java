package com.example.cryptocurrency.dto;

import lombok.Data;

@Data
public class UserDTO {

    private String userName;
    private String symbol;

    private double oldPrice;
}
