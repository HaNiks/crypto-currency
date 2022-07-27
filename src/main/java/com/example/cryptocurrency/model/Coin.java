package com.example.cryptocurrency.model;

import javax.persistence.*;

@Entity
public class Coin {
    @Id
    private int id;
    private String symbol;

    public Coin() {
    }

    public Coin(int id, String symbol) {
        this.id = id;
        this.symbol = symbol;
    }

    public int getId() {
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

    @Override
    public String toString() {
        return "Coin{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}

