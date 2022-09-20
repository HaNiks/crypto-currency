package com.example.cryptocurrency.service;

import com.example.cryptocurrency.exception.CoinNotFoundException;
import com.example.cryptocurrency.model.Coin;
import com.example.cryptocurrency.model.Price;
import com.example.cryptocurrency.model.User;
import com.example.cryptocurrency.repository.CoinRepository;
import com.example.cryptocurrency.repository.PriceRepository;
import com.example.cryptocurrency.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public record CoinService(CoinRepository coinRepository, PriceRepository priceRepository,
                          PriceService priceService, UserRepository userRepository, ModelMapper modelMapper,
                          WebClientService webClientService) {
    @Autowired
    public CoinService {
    }

    public List<Coin> findAll() {
        return coinRepository.findAll();
    }

    public Coin saveNewCoin(int id) {
        Coin coin = getCoinFromAPI(id);
        Price price = new Price();
        price.setId(coin.getId());
        price.setPriceUsd(priceService.getNewPrice(id));
        price.setSymbol(coin.getSymbol());
        coinRepository.save(coin);
        priceRepository.save(price);
        return coin;
    }

    public Coin getCoinFromAPI(int id) {
        String url = "https://api.coinlore.net/api/ticker/?id=" + id;
        Coin[] coins = webClientService.getResponseSpec(url)
                .bodyToMono(Coin[].class)
                .block();
        return Objects.requireNonNull(coins)[0];
    }

    public Coin delete(String symbol) {
        Coin coin = coinRepository.findCoinBySymbol(symbol).orElseThrow(CoinNotFoundException::new);
        coinRepository.delete(coin);
        priceRepository.delete(priceService.findPriceBySymbol(symbol));
        User user = userRepository.findBySymbol(symbol);
        if (user != null) {
            userRepository.delete(user);
        }
        return coin;
    }

    public Coin findCoinBySymbol(String symbol) {
        return coinRepository.findCoinBySymbol(symbol).orElseThrow(CoinNotFoundException::new);
    }
}
