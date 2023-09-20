package com.example.mini_market_wgs.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DtoCashierRequest {
    @JsonProperty("id_cashier")
    private String idCashier;
    @JsonProperty("name")
    private String name;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("address")
    private String address;
    @JsonProperty("is_resign")
    private Boolean isResign;

    public String getIdCashier() {
        return idCashier;
    }

    public void setIdCashier(String idCashier) {
        this.idCashier = idCashier.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address.trim();
    }

    public Boolean getResign() {
        return isResign;
    }

    public void setResign(Boolean resign) {
        isResign = resign;
    }
}
