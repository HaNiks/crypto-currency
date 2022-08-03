package com.example.cryptocurrency.cofiguration;

import com.example.cryptocurrency.model.Coin;
import com.example.cryptocurrency.model.Price;
import com.example.cryptocurrency.repository.CoinRepo;
import com.example.cryptocurrency.repository.PriceRepo;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CryptoConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    CommandLineRunner commandLineRunner(CoinRepo coinRepo, PriceRepo priceRepo) {
        return args -> {
            if (coinRepo.count() == 0) {

                Coin BTC = new Coin(90, "Bitcoin","BTC");
                Coin ETH = new Coin(80, "Ethereum", "ETH");
                Coin SOL = new Coin(48543, "Solana" ,"SOL");

                coinRepo.save(BTC);
                coinRepo.save(ETH);
                coinRepo.save(SOL);
            }
            if (priceRepo.count() == 0) {
                Price BTCPrice = new Price(90, "BTC", 0.0);
                Price ETHPrice = new Price(80, "ETH", 0.0);
                Price SOLPrice = new Price(48543, "SOL", 0.0);

                priceRepo.save(BTCPrice);
                priceRepo.save(ETHPrice);
                priceRepo.save(SOLPrice);
            }
        };
    }
}
