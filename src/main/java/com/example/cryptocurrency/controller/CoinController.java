package com.example.cryptocurrency.controller;

import com.example.cryptocurrency.model.Coin;
import com.example.cryptocurrency.model.Price;
import com.example.cryptocurrency.model.User;
import com.example.cryptocurrency.service.CoinService;
import com.example.cryptocurrency.service.PriceService;
import com.example.cryptocurrency.service.UserService;
import com.example.cryptocurrency.util.UserErrorResponse;
import com.example.cryptocurrency.util.UserNotFoundException;
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
    private final PriceService priceService;
    private final UserService userService;

    @Autowired
    public CoinController(CoinService coinService, PriceService priceService, UserService userService) {
        this.coinService = coinService;
        this.priceService = priceService;
        this.userService = userService;
    }

    @GetMapping
    public List<Coin> getCoin() {
        return coinService.findAll();
    }

    @GetMapping("/{symbol}")
    public Price getPriceBySymbol(@PathVariable("symbol") String symbol) {
        return priceService.updatePrice(symbol);
    }

    @GetMapping("/notify")
    public void notify(@ModelAttribute("username") String username, @ModelAttribute("symbol") String symbol) {
        userService.setUserName(username, symbol);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handlerException(UserNotFoundException exception) {
        UserErrorResponse response = new UserErrorResponse("User not found",
                LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
