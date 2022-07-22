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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RateService {

    public BankApiDto[] getRate()
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<BankApiDto[]> exchange = restTemplate.exchange("http://api.nbp.pl/api/exchangerates/tables/a",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                BankApiDto[].class);
        return exchange.getBody();
    }
    public BigDecimal calculate(BigDecimal amount, CurrencyName fromCurrency, CurrencyName toCurrency) {

        if (fromCurrency==(CurrencyName.PLN))
        {
            if(toCurrency==(CurrencyName.PLN)) {
                return amount;
            }
            else return amount.divide(searchCurrencyValue(toCurrency), MathContext.DECIMAL128);
        }
        if (toCurrency==(CurrencyName.PLN))
        {
            return amount.multiply(searchCurrencyValue(fromCurrency), MathContext.DECIMAL128);
        }

        else return amount.multiply(convertCurrency(fromCurrency,toCurrency));
    }

    private BigDecimal convertCurrency(CurrencyName fromCurrency, CurrencyName toCurrency) {
        BigDecimal currency= searchCurrencyValue(fromCurrency);
        BigDecimal currency2 = searchCurrencyValue(toCurrency);
        return currency.divide(currency2);
    }
    @NotNull
    private BigDecimal searchCurrencyValue(CurrencyName currencyToBeConverted) {
        BigDecimal parse = BigDecimal.valueOf(getRatedDtoStream()
                .filter(api->api.getCode()
                        .equalsIgnoreCase(currencyToBeConverted.name()))
                .mapToDouble(rates-> rates.getMid()).sum());
        return Optional.of(parse).orElseThrow();

    }
    public Stream<RatesDto> getRatedDtoStream()
    {
        return Arrays.stream(getRate()).map(BankApiDto::getRates).flatMap(Collection::stream);

    }
        public List<RatesDto> sortCurrencyFromHighestToLowestValue() {
        return getRatedDtoStream()
                .sorted(Comparator.comparing(RatesDto::getMid).reversed())
                .collect(Collectors.toList());

        }
}
