package com.example.cryptocurrency.dto;

import java.io.Serializable;
import java.util.Objects;

public class CoinDTO implements Serializable {
    private int id;
    private String symbol;

    public CoinDTO(int id, String symbol) {
        this.id = id;
        this.symbol = symbol;
    }

    public CoinDTO() {
    }

    public int getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoinDTO entity = (CoinDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.symbol, entity.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, symbol);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "symbol = " + symbol + ")";
    }
}
