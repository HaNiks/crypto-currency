package com.example.cryptocurrency.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Price")
@Table(name = "price_coin")
public class Price {

    @Id
    @JsonProperty("id")
    private int id;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("price_usd")
    @Column(name = "price")
    private double price;

    public Price() {
    }

    public Price(int id, String symbol, double price) {
        this.id = id;
        this.symbol = symbol;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", price=" + price +
                '}';
    }
}
