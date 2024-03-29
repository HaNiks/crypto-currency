package com.example.cryptocurrency.controller;

import com.example.cryptocurrency.dto.CoinDTO;
import com.example.cryptocurrency.model.Security;
import com.example.cryptocurrency.security.SecurityDetails;
import com.example.cryptocurrency.service.CoinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Coin", description = "Operations intended for coins")
@RestController
@RequestMapping("/coin")
@RequiredArgsConstructor
public class CoinController {

    private final CoinService coinService;

    @Operation(summary = "Get all coins", tags = "Coin",
            description = "Gets all coins")
    @GetMapping
    public List<CoinDTO> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityDetails securityDetails = (SecurityDetails) authentication.getPrincipal();
        Security security = securityDetails.getSecurity();
        System.out.println(security);
        return coinService.findAll();
    }

    @Operation(summary = "Add new coin", tags = "Coin",
            description = "Add new coin by id")
    @Parameter(name = "id", description = "Enter id", example = "80")
    @PostMapping("/add")
    public CoinDTO add(@RequestParam int id) {
        return coinService.save(id);
    }


    @Operation(summary = "Delete coin", tags = "Coin",
            description = "Removes a coin by symbol")
    @Parameter(name = "symbol", description = "Enter symbol", example = "ETH")
    @DeleteMapping("/delete")
    public CoinDTO delete(@RequestParam String symbol) {
        return coinService.delete(symbol);
    }

}
