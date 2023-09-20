package com.example.mini_market_wgs.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DtoItemRequest {
    @JsonProperty("id_item")
    private String idItem;
    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private Integer price;

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
