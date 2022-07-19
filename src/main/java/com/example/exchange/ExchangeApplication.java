package com.example.exchange;

import com.example.exchange.Currency.CurrencyName;
import com.example.exchange.Rate.RateService;
import com.example.exchange.Rate.RatesDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class ExchangeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExchangeApplication.class, args);
var RatesService= new RateService();
var bankApi = RatesService.getRate();
        System.out.println("tutaj");
        String nowy = "usd";

        List<RatesDto> ratesDtoList= bankApi[0].getRates().stream().collect(Collectors.toList());
        System.out.println("ratesdtolist:"+ratesDtoList);
        System.out.println(ratesDtoList
                .stream()
                .map(RatesDto::getCode)
                .collect(Collectors.toList()));

        System.out.println(ratesDtoList
                .stream()
                .filter(p->p.getCode().equalsIgnoreCase(nowy))
                        .map(RatesDto::getMid)
                .collect(Collectors.toList()));
        System.out.println(ratesDtoList
                .stream()
                .filter(p->p.getCode().equalsIgnoreCase(CurrencyName.EUR.name()))
                .mapToDouble(RatesDto::getMid));


    }

}
