package com.example.cryptocurrency.model;

public class User {
    private int id;

    private String userName;
    private String symbol;

    private double oldPrice;
    private double newPrice;

    public User() {
    }

    public User(int id, String userName, String symbol, double oldPrice, double newPrice) {
        this.id = id;
        this.userName = userName;
        this.symbol = symbol;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
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

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
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
