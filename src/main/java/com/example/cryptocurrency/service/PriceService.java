package com.example.cryptocurrency.service;

import com.example.cryptocurrency.model.Price;
import com.example.cryptocurrency.repository.PriceRepo;
import com.example.cryptocurrency.util.CoinNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class PriceService {

    private final RestTemplate restTemplate;
    private final PriceRepo priceRepo;


    @Autowired
    public PriceService(PriceRepo priceRepo) {
        this.restTemplate = new RestTemplate();
        this.priceRepo = priceRepo;
    }

    public Price updatePrice(String symbol) {
        Price priceObj = this.findPrice(symbol);
        priceObj.setPrice(this.getNewPrice(symbol));
        priceRepo.save(priceObj);
        return findPrice(symbol);
    }

    public double getNewPrice(String symbol) {
        Price price = findPrice(symbol);
        String url = "https://api.coinlore.net/api/ticker/?id=" + price.getId();
        Price[] prices = restTemplate.getForObject(url, Price[].class);
        return Objects.requireNonNull(prices)[0].getPrice();
    }

    public Price findPrice(String symbol) {
        return priceRepo.getPriceBySymbol(symbol).orElseThrow(CoinNotFoundException::new);
    }
}