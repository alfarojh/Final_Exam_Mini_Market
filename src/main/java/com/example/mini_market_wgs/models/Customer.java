package com.example.mini_market_wgs.models;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Customer extends BaseModel{
    @Column(unique = true)
    private String idCustomer;
    private String name;
    private String phoneNumber;

    public Customer() {
        // Do Nothing
    }

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
