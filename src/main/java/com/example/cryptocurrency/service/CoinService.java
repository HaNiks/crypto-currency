package com.example.cryptocurrency.service;

import com.example.cryptocurrency.exception.CoinNotFoundException;
import com.example.cryptocurrency.model.Price;
import com.example.cryptocurrency.model.User;
import com.example.cryptocurrency.model.Coin;
import com.example.cryptocurrency.repository.CoinRepository;
import com.example.cryptocurrency.repository.PriceRepository;
import com.example.cryptocurrency.repository.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Service
public record CoinService(CoinRepository coinRepo, PriceRepository priceRepo,
                          PriceService priceService, UserRepository userRepo) {

    public List<Coin> findAll() {
        return coinRepo.findAll();
    }

    public Coin saveNewCoin(int id) {
        Coin coin = getCoinFromAPI(id);
        Price price = new Price();
        price.setId(coin.getId());
        price.setPriceUsd(priceService.getNewPrice(id));
        price.setSymbol(coin.getSymbol());
        coinRepo.save(coin);
        priceRepo.save(price);
        return coin;
    }

    public Coin getCoinFromAPI(int id) {
        String url = "https://api.coinlore.net/api/ticker/?id=" + id;
        WebClient webClient = WebClient.create(url);
        Coin[] coins = webClient.get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Coin[].class)
                .block();
        return Objects.requireNonNull(coins)[0];
    }

    public Coin delete(String symbol) {
        Coin coin = coinRepo.findCoinBySymbol(symbol).orElseThrow(CoinNotFoundException::new);
        coinRepo.delete(coin);
        priceRepo.delete(priceService.findPrice(symbol));
        User user = userRepo.findBySymbol(symbol);
        if (user != null) {
            userRepo.delete(user);
        }
        return coin;
    }

    public Coin findCoinBySymbol(String symbol) {
        return coinRepo.findCoinBySymbol(symbol).orElseThrow(CoinNotFoundException::new);
    }
}
