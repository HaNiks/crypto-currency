package com.example.cryptocurrency.service;

import com.example.cryptocurrency.model.Price;
import com.example.cryptocurrency.exception.CoinNotFoundException;
import com.example.cryptocurrency.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PriceService {

    @Value("${url}")
    private String url;

    private final PriceRepository priceRepo;
    private final RestTemplate restTemplate = new RestTemplate();


    public Price updatePrice(String symbol) {
        Price priceObj = this.findPrice(symbol);
        priceObj.setPriceUsd(this.getNewPrice(priceObj.getId()));
        priceRepo.save(priceObj);
        return findPrice(symbol);
    }

    public double getNewPrice(int id) {
        Price[] prices = restTemplate.getForObject(url + id, Price[].class);
        return Objects.requireNonNull(prices)[0].getPriceUsd();
    }

    public Price findPrice(String symbol) {
        return priceRepo.findPriceBySymbol(symbol).orElseThrow(CoinNotFoundException::new);
    }

    public List<Price> findAll() {
        return priceRepo.findAll();
    }
}