package com.example.cryptocurrency.controller;

import com.example.cryptocurrency.model.Coin;
import com.example.cryptocurrency.service.CoinService;
import com.example.cryptocurrency.util.CoinErrorResponse;
import com.example.cryptocurrency.util.CoinNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coin")
public class CoinController {

    private CoinService coinService;

    @Autowired
    public CoinController(CoinService coinService) {
        this.coinService = coinService;
    }

    @GetMapping
    public List<Coin> getCoin() {
        return coinService.findAll();
    }

    @GetMapping("/save")
    public void saveCoin() {
        coinService.save();
    }

    @GetMapping("/{id}")
    public Coin getCoinById(@PathVariable("id") int id) {
        return coinService.findById(id);
    }

    @ExceptionHandler
    private ResponseEntity<CoinErrorResponse> handlerException(CoinNotFoundException exception) {
        CoinErrorResponse response = new CoinErrorResponse("Coin with this id wasn't found",
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
