package com.example.exchange.Rate;

import org.hibernate.procedure.spi.ParameterRegistrationImplementor;

public class RatesDto {
    private  String currency;
    private String code;
    private  float mid;

    public RatesDto() {

    }

    public RatesDto(String currency, String code, float mid) {
        this.currency = currency;
        this.code = code;
        this.mid = mid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getMid() {
        return mid;
    }

    public void setMid(float mid) {
        this.mid = mid;
    }
}
