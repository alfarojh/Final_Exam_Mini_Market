package com.example.mini_market_wgs.dto.responses;

import com.example.mini_market_wgs.models.ItemRelational;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DtoItemRelationalResponse {
    @JsonProperty("name_item_1")
    private String nameItem1;
    @JsonProperty("name_item_2")
    private String nameItem2;
    @JsonProperty("purchased")
    private int purchased;

    public DtoItemRelationalResponse(ItemRelational itemRelational) {
        this.nameItem1 = itemRelational.getItem1().getName();
        this.nameItem2 = itemRelational.getItem2().getName();
        this.purchased = itemRelational.getCount();
    }

    public String getNameItem1() {
        return nameItem1;
    }

    public void setNameItem1(String nameItem1) {
        this.nameItem1 = nameItem1;
    }

    public String getNameItem2() {
        return nameItem2;
    }

    public void setNameItem2(String nameItem2) {
        this.nameItem2 = nameItem2;
    }

    public int getPurchased() {
        return purchased;
    }

    public void setPurchased(int purchased) {
        this.purchased = purchased;
    }
}
