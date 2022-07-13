package com.example.cryptocurrency.controller;

import com.example.cryptocurrency.model.Coin;
import com.example.cryptocurrency.service.CoinService;
import com.example.cryptocurrency.service.NotificationService;
import com.example.cryptocurrency.util.CoinErrorResponse;
import com.example.cryptocurrency.util.CoinNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/coin")
public class CoinController {

    private final CoinService coinService;
    private final NotificationService notificationService;

    @Autowired
    public CoinController(CoinService coinService, NotificationService notificationService) {
        this.coinService = coinService;
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<Coin> getCoin() {
        return coinService.findAll();
    }

    @GetMapping("/{id}")
    public Coin getCoinById(@PathVariable("id") int id) {
        return coinService.findById(id);
    }

    @GetMapping("/notify")
    public void notify(@ModelAttribute("username") String username, @ModelAttribute("symbol") String symbol) {
        notificationService.notifyUser(username, symbol);
    }

    @ExceptionHandler
    private ResponseEntity<CoinErrorResponse> handlerException(CoinNotFoundException exception) {
        CoinErrorResponse response = new CoinErrorResponse("Coin with this id wasn't found",
                LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
