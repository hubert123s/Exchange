package com.example.exchange.Rate;

import com.example.exchange.Currency.CurrencyName;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/currency")
public class RateController {

    private  final RateService rateService;

    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @GetMapping
    @ResponseBody
    BigDecimal currency(@RequestParam BigDecimal amount,
                        @RequestParam CurrencyName fromCurrency,
                        @RequestParam CurrencyName toCurrency)
    {

        return rateService.calculate(amount,fromCurrency,toCurrency);
    }
    @GetMapping("/highestvalue")
    @ResponseBody
    List<RatesDto> highestValue()
    {

        return rateService.sortCurrencyFromHighestToLowestValue();
    }


}
