package com.example.mini_market_wgs.models;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Customer extends BaseModel{
    @Column(unique = true)
    private String idCustomer;
    private String name;
    private String phoneNumber;
    private Integer year;

    public Customer() {
        // Do Nothing
    }

    public Customer(String idCustomer, Integer year, String name, String phoneNumber) {
        this.idCustomer = idCustomer;
        this.year = year;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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
