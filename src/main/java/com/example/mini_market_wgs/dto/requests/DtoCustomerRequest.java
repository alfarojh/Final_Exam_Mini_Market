package com.example.mini_market_wgs.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DtoCustomerRequest {
    @JsonProperty("id_customer")
    public String idCustomer;
    @JsonProperty("name")
    public String name;
    @JsonProperty("phone_number")
    public String phoneNumber;

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
