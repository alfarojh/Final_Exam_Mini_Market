package com.example.mini_market_wgs.dto.responses;

import com.example.mini_market_wgs.models.Item;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DtoItemResponse {
    @JsonProperty("id_item")
    private String idItem;
    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private Integer price;
    @JsonProperty("total_purchase")
    private Integer totalPurchase;

    public DtoItemResponse(Item item) {
        this.idItem = item.getIdItem();
        this.name = item.getName();
        this.price = item.getPrice();
        this.totalPurchase = item.getQuantityPurchased() - item.getQuantityReturned();
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getTotalPurchase() {
        return totalPurchase;
    }

    public void setTotalPurchase(Integer totalPurchase) {
        this.totalPurchase = totalPurchase;
    }
}
