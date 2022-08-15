package com.example.cryptocurrency.service;

import com.example.cryptocurrency.dto.PriceDTO;
import com.example.cryptocurrency.model.Price;
import com.example.cryptocurrency.repository.PriceRepository;
import com.example.cryptocurrency.exception.CoinNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PriceService {
    private final PriceRepository priceRepository;
    private final ModelMapper modelMapper;
    private final WebClientService webClientService;
    @Value("${url}")
    private String url;

    @Autowired
    public PriceService(PriceRepository priceRepository, ModelMapper modelMapper, WebClientService webClientService) {
        this.priceRepository = priceRepository;
        this.modelMapper = modelMapper;
        this.webClientService = webClientService;
    }

    public Price updatePrice(String symbol) {
        Price priceObj = this.findPriceBySymbol(symbol);
        priceObj.setPriceUsd(this.getNewPrice(priceObj.getId()));
        return priceRepository.save(priceObj);
    }

    public double getNewPrice(int id) {
        url += id;
        Price[] prices = webClientService.getResponseSpec(url)
                .bodyToMono(Price[].class)
                .block();
        return Objects.requireNonNull(prices)[0].getPriceUsd();
    }

    public Price findPriceBySymbol(String symbol) {
        return priceRepository.findPriceBySymbol(symbol).orElseThrow(CoinNotFoundException::new);
    }

    public List<Price> findAll() {
        return priceRepository.findAll();
    }

    public PriceDTO convertToPriceDTO(Price price) {
        return modelMapper.map(price, PriceDTO.class);
    }
}