package com.example.mini_market_wgs.models;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Item extends BaseModel{
    @Column(unique = true)
    private String idItem;
    private String name;
    private Integer price;
    private Integer quantityPurchased = 0;
    private Integer quantityReturned = 0;

    public Item() {
        // Do Nothing
    }

    public Item(String idItem, String name, Integer price) {
        this.idItem = idItem;
        this.name = name;
        this.price = price;
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

    public Integer getQuantityPurchased() {
        return quantityPurchased;
    }

    public void setQuantityPurchased(Integer quantityPurchased) {
        this.quantityPurchased = quantityPurchased;
    }

    public void addQuantityPurchased(int quantityPurchased) {
        this.quantityPurchased += quantityPurchased;
    }
    public Integer getQuantityReturned() {
        return quantityReturned;
    }

    public void setQuantityReturned(Integer quantityReturned) {
        this.quantityReturned = quantityReturned;
    }
}
