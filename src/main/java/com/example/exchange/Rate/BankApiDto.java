package com.example.exchange.Rate;

import com.example.exchange.Rate.RatesDto;

import java.time.LocalDate;
import java.util.List;

public class BankApiDto {
    private String table;
    private  String no;
    private  LocalDate effectiveDate;
     private  List<RatesDto> rates;



    public BankApiDto() {

    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public List<RatesDto> getRates() {
        return rates;
    }

    public void setRates(List<RatesDto> rates) {
        this.rates = rates;
    }

    public BankApiDto(String table, String no, LocalDate effectiveDate, List<RatesDto> rates) {
        this.table = table;
        this.no = no;
        this.effectiveDate = effectiveDate;
        this.rates = rates;
    }
    @Override
    public String toString() {
        return "BankApiDto{" +
                "table='" + table + '\'' +
                ", no='" + no + '\'' +
                ", effectiveDate=" + effectiveDate +
                ", rates=" + rates +
                '}';
    }
}
