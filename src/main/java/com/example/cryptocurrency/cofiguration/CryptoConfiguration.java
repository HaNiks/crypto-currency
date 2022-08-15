package com.example.cryptocurrency.cofiguration;

import com.example.cryptocurrency.model.Coin;
import com.example.cryptocurrency.model.Price;
import com.example.cryptocurrency.repository.CoinRepository;
import com.example.cryptocurrency.repository.PriceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class CryptoConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    CommandLineRunner commandLineRunner(CoinRepository coinRepository, PriceRepository priceRepository) {
        return args -> {
            if (coinRepository.count() == 0) {

                Coin BTC = new Coin(90, "Bitcoin", "BTC");
                Coin ETH = new Coin(80, "Ethereum", "ETH");
                Coin SOL = new Coin(48543, "Solana", "SOL");

                coinRepository.saveAll(Arrays.asList(BTC, ETH, SOL));
            }
            if (priceRepository.count() == 0) {
                Price BTCPrice = new Price(90, "BTC", 0.0);
                Price ETHPrice = new Price(80, "ETH", 0.0);
                Price SOLPrice = new Price(48543, "SOL", 0.0);

                priceRepository.saveAll(Arrays.asList(BTCPrice, ETHPrice, SOLPrice));
            }
        };
    }
}
