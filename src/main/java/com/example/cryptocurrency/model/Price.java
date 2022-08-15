package com.example.cryptocurrency.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Price {

    @Id
    private int id;

    private String symbol;

    @Column(name = "price")
    private double priceUsd;

    public Price() {
    }

    public Price(int id, String symbol, double priceUsd) {
        this.id = id;
        this.symbol = symbol;
        this.priceUsd = priceUsd;
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

    public double getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(double priceUsd) {
        this.priceUsd = priceUsd;
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", price=" + priceUsd +
                '}';
    }
}
