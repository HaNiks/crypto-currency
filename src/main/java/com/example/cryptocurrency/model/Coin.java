package com.example.cryptocurrency.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Coin {

    @Id
    private int id;
    private String symbol;
    private String name;
    private double price;

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

