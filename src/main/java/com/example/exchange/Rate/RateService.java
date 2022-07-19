package com.example.exchange.Rate;

import com.example.exchange.Currency.CurrencyName;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RateService {

    public BankApiDto[] getRate()
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<BankApiDto[]> exchange = restTemplate.exchange("http://api.nbp.pl/api/exchangerates/tables/a",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                BankApiDto[].class);
       // List<BankApiDto> familyMembers = Arrays.stream(exchange.getBody()).collect(Collectors.toList());
        return exchange.getBody();
    }

    public BigDecimal calculate(BigDecimal amount, CurrencyName fromCurrency, CurrencyName toCurrency) {
        if (fromCurrency==(CurrencyName.PLN))
        {
            return amount.divide(BigDecimal.valueOf(searchCurrencyValue(toCurrency)), MathContext.DECIMAL128);
        }
        if (toCurrency==(CurrencyName.PLN))
        {
            return amount.multiply(BigDecimal.valueOf(searchCurrencyValue(fromCurrency)));
        }
        else return amount.multiply(BigDecimal.valueOf(convertCurrency(fromCurrency,toCurrency)));
    }

    private double convertCurrency(CurrencyName fromCurrency, CurrencyName toCurrency) {
        double currency= searchCurrencyValue(fromCurrency);
        double currency2 = searchCurrencyValue(toCurrency);
        return currency/currency2;
    }
    @NotNull
    private Double searchCurrencyValue(CurrencyName currencyToBeConverted) {
        List<BankApiDto> bankApiDtoList = Arrays.stream(getRate()).toList();
        return bankApiDtoList.stream().map(BankApiDto::getRates)
                .flatMap(Collection::stream)
                .filter(api->api.getCode()
                        .equalsIgnoreCase(currencyToBeConverted.name()))
                .mapToDouble(rates-> rates.getMid()).sum();

    }

}
