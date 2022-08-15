package com.example.cryptocurrency.controller;

import com.example.cryptocurrency.dto.PriceDTO;
import com.example.cryptocurrency.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PriceController {

    private final PriceService priceService;

    @Autowired
    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("/coin/{symbol}")
    public PriceDTO getBySymbol(@PathVariable("symbol") String symbol) {
        return priceService.convertToPriceDTO(priceService.updatePrice(symbol));
    }

    @GetMapping("/price")
    public List<PriceDTO> findAll() {
        return priceService.findAll()
                .stream()
                .map(priceService::convertToPriceDTO)
                .collect(Collectors.toList());
    }

}
