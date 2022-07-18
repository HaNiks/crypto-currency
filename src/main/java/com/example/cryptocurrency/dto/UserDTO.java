package com.example.cryptocurrency.dto;

public class UserDTO {

    private String userName;
    private String symbol;

    private double oldPrice;

    public UserDTO() {
    }

    public UserDTO(String userName, String symbol, double oldPrice) {
        this.userName = userName;
        this.symbol = symbol;
        this.oldPrice = oldPrice;
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
}
