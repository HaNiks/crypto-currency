package com.example.cryptocurrency.service;

import com.example.cryptocurrency.model.Price;
import com.example.cryptocurrency.repository.PriceRepo;
import com.example.cryptocurrency.exception.CoinNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Service
public record PriceService(PriceRepo priceRepo) {

    @Autowired
    public PriceService {
    }

    public Price updatePrice(String symbol) {
        Price priceObj = this.findPrice(symbol);
        priceObj.setPriceUsd(this.getNewPrice(priceObj.getId()));
        priceRepo.save(priceObj);
        return findPrice(symbol);
    }

    public double getNewPrice(int id) {
        String url = "https://api.coinlore.net/api/ticker/?id=" + id;
        WebClient webClient = WebClient.create(url);
        Price[] prices = webClient.get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Price[].class)
                .block();
        return Objects.requireNonNull(prices)[0].getPriceUsd();
    }

    public Price findPrice(String symbol) {
        return priceRepo.getPriceBySymbol(symbol).orElseThrow(CoinNotFoundException::new);
    }

    public List<Price> getAllPrice() {
        return priceRepo.findAll();
    }
}