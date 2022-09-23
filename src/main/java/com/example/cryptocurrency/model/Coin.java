package com.example.cryptocurrency.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Coin {
    @Id
    private int id;
    private String name;
    private String symbol;
}

