package com.example.cryptocurrency.controller;

import com.example.cryptocurrency.dto.CoinDTO;
import com.example.cryptocurrency.model.Coin;
import com.example.cryptocurrency.service.CoinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Coin", description = "Operations intended for coins")
@RestController
@RequestMapping("/coin")
@RequiredArgsConstructor
public class CoinController {

    private final CoinService coinService;
    private final ModelMapper modelMapper;

    @Operation(summary = "Get all coins", tags = "Coin",
            description = "Gets all coins")
    @GetMapping
    public List<CoinDTO> findAll() {
        return coinService.findAll().stream()
                .map(this::convertToCoinDTO)
                .toList();
    }

    @Operation(summary = "Add new coin", tags = "Coin",
            description = "Add new coin by id")
    @Parameter(name = "id", description = "Enter id", example = "10")
    @PostMapping("/add")
    public CoinDTO add(@RequestParam int id) {
        return convertToCoinDTO(coinService.saveNewCoin(id));
    }


    @Operation(summary = "Delete coin", tags = "Coin",
            description = "Removes a coin by symbol")
    @Parameter(name = "symbol", description = "Enter symbol", example = "ETH")
    @DeleteMapping("/delete")
    public CoinDTO delete(@RequestParam String symbol) {
        return convertToCoinDTO(coinService.delete(symbol));
    }

    private CoinDTO convertToCoinDTO(Coin coin) {
        return modelMapper.map(coin, CoinDTO.class);
    }

}
