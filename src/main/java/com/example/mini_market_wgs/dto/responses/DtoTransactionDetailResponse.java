package com.example.mini_market_wgs.dto.responses;

import com.example.mini_market_wgs.models.TransactionDetail;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DtoTransactionDetailResponse {
    @JsonProperty("name_item")
    private String nameItem;
    @JsonProperty("price")
    private Integer price;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("total_price")
    private Integer totalPrice;

    public DtoTransactionDetailResponse(TransactionDetail transactionDetail) {
        this.nameItem = transactionDetail.getItem().getName();
        this.price = transactionDetail.getPrice();
        this.quantity = transactionDetail.getQuantity();
        this.totalPrice = transactionDetail.getTotalPrice();
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }
}
