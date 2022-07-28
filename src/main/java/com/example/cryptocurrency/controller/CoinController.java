package com.example.cryptocurrency.controller;

import com.example.cryptocurrency.dto.CoinDTO;
import com.example.cryptocurrency.model.Coin;
import com.example.cryptocurrency.service.CoinService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/coin")
public class CoinController {

    private final CoinService coinService;
    private final ModelMapper modelMapper;

    @Autowired
    public CoinController(CoinService coinService, ModelMapper modelMapper) {
        this.coinService = coinService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<CoinDTO> getAllCoins() {
        return coinService.findAll().stream()
                .map(this::convertToCoinDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/add")
    public CoinDTO addCoin(@RequestParam int id) {
        return convertToCoinDTO(coinService.saveNewCoin(id));
    }

    @PostMapping("/delete")
    public CoinDTO deleteCoin(@RequestParam String symbol) {
        return convertToCoinDTO(coinService.deleteCoinBySymbol(symbol));
    }

    private CoinDTO convertToCoinDTO(Coin coin) {
        return modelMapper.map(coin, CoinDTO.class);
    }
}
