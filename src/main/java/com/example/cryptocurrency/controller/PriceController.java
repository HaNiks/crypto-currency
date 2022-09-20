package com.example.cryptocurrency.controller;

import com.example.cryptocurrency.dto.PriceDTO;
import com.example.cryptocurrency.model.Price;
import com.example.cryptocurrency.service.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Price coin", description = "Operations intended for price coins")
@RestController
public class PriceController {

    private final PriceService priceService;
    private final ModelMapper modelMapper;

    @Autowired
    public PriceController(PriceService priceService, ModelMapper modelMapper) {
        this.priceService = priceService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Get coin pryce", tags = "Price",
            description = "Gets price of the coin by symbol")
    @Parameter(name = "symbol", description = "Enter symbol", example = "ETH")
    @GetMapping("/coin/{symbol}")
    public PriceDTO getBySymbol(@PathVariable("symbol") String symbol) {
        return convertToPriceDTO(priceService.updatePrice(symbol));
    }

    @Operation(summary = "Get all coin prices", tags = "Price",
            description = "Gets all prices")
    @GetMapping("/price")
    public List<PriceDTO> findAll() {
        return priceService.findAll()
                .stream()
                .map(this::convertToPriceDTO)
                .toList();
    }

    private PriceDTO convertToPriceDTO(Price price) {
        return modelMapper.map(price, PriceDTO.class);
    }
}
