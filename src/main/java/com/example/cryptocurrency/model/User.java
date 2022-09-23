package com.example.cryptocurrency.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Data
@Slf4j
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_name")
    private String userName;
    private String symbol;
    @Column(name = "old_price")
    private double oldPrice;
}
