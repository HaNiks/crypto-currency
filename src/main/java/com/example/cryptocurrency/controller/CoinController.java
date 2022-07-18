package com.example.cryptocurrency.controller;

import com.example.cryptocurrency.dto.CoinDTO;
import com.example.cryptocurrency.dto.PriceDTO;
import com.example.cryptocurrency.dto.UserDTO;
import com.example.cryptocurrency.model.Coin;
import com.example.cryptocurrency.model.Price;
import com.example.cryptocurrency.model.User;
import com.example.cryptocurrency.service.CoinService;
import com.example.cryptocurrency.service.PriceService;
import com.example.cryptocurrency.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/coin")
public class CoinController {

    private final CoinService coinService;
    private final PriceService priceService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final List<UserDTO> users;

    @Autowired
    public CoinController(CoinService coinService, PriceService priceService,
                          UserService userService, ModelMapper modelMapper) {
        this.coinService = coinService;
        this.priceService = priceService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.users = new ArrayList<>();
    }

    @GetMapping
    public List<CoinDTO> getCoin() {
        return coinService.findAll().stream()
                .map(this::convertToCoinDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{symbol}")
    public PriceDTO getPriceBySymbol(@PathVariable("symbol") String symbol) {
        return convertToPriceDTO(priceService.updatePrice(symbol));
    }

    @GetMapping("/notify")
    public List<UserDTO> notify(@ModelAttribute("username") String username, @ModelAttribute("symbol") String symbol) {
        return convertToUserDTO(userService.setUserName(username, symbol));
    }

    private CoinDTO convertToCoinDTO(Coin coin) {
        return modelMapper.map(coin, CoinDTO.class);
    }

    private PriceDTO convertToPriceDTO(Price price) {
        return modelMapper.map(price, PriceDTO.class);
    }

    private List<UserDTO> convertToUserDTO(User user) {
        users.add(modelMapper.map(user, UserDTO.class));
        return users;
    }
}
