package com.example.cryptocurrency.model;

import javax.persistence.*;

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

    public User() {
    }

    public User(int id, String userName, String symbol, double oldPrice) {
        this.id = id;
        this.userName = userName;
        this.symbol = symbol;
        this.oldPrice = oldPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", symbol='" + symbol + '\'' +
                ", oldPrice=" + oldPrice +
                '}';
    }
}
