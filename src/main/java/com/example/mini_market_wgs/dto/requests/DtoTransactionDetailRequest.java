package com.example.mini_market_wgs.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DtoTransactionDetailRequest {
    @JsonProperty("id_item")
    private String idItem;
    @JsonProperty("quantity")
    private Integer quantity;

    public DtoTransactionDetailRequest() {
        // Do Nothing
    }

    public DtoTransactionDetailRequest(String idItem, Integer quantity) {
        this.idItem = idItem;
        this.quantity = quantity;
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
