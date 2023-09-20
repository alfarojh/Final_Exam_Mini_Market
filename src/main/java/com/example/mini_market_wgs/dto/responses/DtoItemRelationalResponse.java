package com.example.mini_market_wgs.dto.responses;

import com.example.mini_market_wgs.models.ItemRelational;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DtoItemRelationalResponse {
    @JsonProperty("id_item1")
    private String idItem1;
    @JsonProperty("name_item1")
    private String nameItem1;
    @JsonProperty("id_item2")
    private String idItem2;
    @JsonProperty("name_item2")
    private String nameItem2;
    @JsonProperty("purchased")
    private int purchased;

    public DtoItemRelationalResponse(ItemRelational itemRelational) {
        this.idItem1 = itemRelational.getItem1().getIdItem();
        this.nameItem1 = itemRelational.getItem1().getName();
        this.idItem2 = itemRelational.getItem2().getIdItem();
        this.nameItem2 = itemRelational.getItem2().getName();
        this.purchased = itemRelational.getCount();
    }

    public String getIdItem1() {
        return idItem1;
    }

    public void setIdItem1(String idItem1) {
        this.idItem1 = idItem1;
    }

    public String getNameItem1() {
        return nameItem1;
    }

    public void setNameItem1(String nameItem1) {
        this.nameItem1 = nameItem1;
    }

    public String getIdItem2() {
        return idItem2;
    }

    public void setIdItem2(String idItem2) {
        this.idItem2 = idItem2;
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
