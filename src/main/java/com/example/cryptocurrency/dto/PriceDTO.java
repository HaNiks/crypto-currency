package com.example.cryptocurrency.dto;

import java.util.Objects;

public class PriceDTO {
    private String symbol;
    private double priceUsd;

    public PriceDTO(String symbol, double priceUsd) {
        this.symbol = symbol;
        this.priceUsd = priceUsd;
    }

    public PriceDTO() {
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPriceUsd() {
        return priceUsd;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setPriceUsd(double priceUsd) {
        this.priceUsd = priceUsd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceDTO entity = (PriceDTO) o;
        return Objects.equals(this.symbol, entity.symbol) &&
                Objects.equals(this.priceUsd, entity.priceUsd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, priceUsd);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "symbol = " + symbol + ", " +
                "priceUsd = " + priceUsd + ")";
    }
}
