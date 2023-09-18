package com.example.mini_market_wgs.dto.responses;

import com.example.mini_market_wgs.models.Cashier;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DtoCashierResponse {
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
    @JsonProperty("joined_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date joinedAt;
    @JsonProperty("resigned_at")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date resignedAt;

    public DtoCashierResponse(Cashier cashier) {
        this.idCashier = cashier.getIdCashier();
        this.name = cashier.getName();
        this.phoneNumber = cashier.getPhoneNumber();
        this.address = cashier.getAddress();
        this.isResign = cashier.getResign();
        this.joinedAt = cashier.getCreatedAt();
        this.resignedAt = cashier.getResignedAt();
    }

    public String getIdCashier() {
        return idCashier;
    }

    public void setIdCashier(String idCashier) {
        this.idCashier = idCashier;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getIsResign() {
        return isResign;
    }

    public void setResign(Boolean resign) {
        isResign = resign;
    }

    public Date getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Date joinedAt) {
        this.joinedAt = joinedAt;
    }

    public Date getResignedAt() {
        return resignedAt;
    }

    public void setResignedAt(Date resignedAt) {
        this.resignedAt = resignedAt;
    }
}
