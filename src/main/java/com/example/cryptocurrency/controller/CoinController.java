package com.example.cryptocurrency.controller;

import com.example.cryptocurrency.dto.CoinDTO;
import com.example.cryptocurrency.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/coin")
public class CoinController {

    private final CoinService coinService;


    @Autowired
    public CoinController(CoinService coinService) {
        this.coinService = coinService;
    }

    @GetMapping
    public List<CoinDTO> findAll() {
        return coinService.findAll().stream()
                .map(coinService::convertToCoinDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/add")
    public CoinDTO add(@RequestParam int id) {
        return coinService.convertToCoinDTO(coinService.saveNewCoin(id));
    }

    @PostMapping("/delete")
    public CoinDTO delete(@RequestParam String symbol) {
        return coinService.convertToCoinDTO(coinService.deleteAllCoinInfo(symbol));
    }
}
