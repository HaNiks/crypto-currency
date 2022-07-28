package com.example.cryptocurrency.controller;

import com.example.cryptocurrency.dto.PriceDTO;
import com.example.cryptocurrency.model.Price;
import com.example.cryptocurrency.service.PriceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PriceController {

    private final PriceService priceService;
    private final ModelMapper modelMapper;

    @Autowired
    public PriceController(PriceService priceService, ModelMapper modelMapper) {
        this.priceService = priceService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/coin/{symbol}")
    public PriceDTO getPriceBySymbol(@PathVariable("symbol") String symbol) {
        return convertToPriceDTO(priceService.updatePrice(symbol));
    }

    @GetMapping("/price")
    public List<PriceDTO> getAllPrice() {
        return priceService.getAllPrice()
                .stream()
                .map(this::convertToPriceDTO)
                .collect(Collectors.toList());
    }

    private PriceDTO convertToPriceDTO(Price price) {
        return modelMapper.map(price, PriceDTO.class);
    }
}
