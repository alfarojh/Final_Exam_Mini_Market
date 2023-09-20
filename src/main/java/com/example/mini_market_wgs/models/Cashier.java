package com.example.mini_market_wgs.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Cashier extends BaseModel {
    @Column(unique = true, name = "id_cashier")
    private String idCashier;
    private String name;
    private String phoneNumber;
    private String address;
    private Boolean isResign = false;
    private Date resignedAt;

    public Cashier() {
        // Do Nothing
    }

    public Cashier(String idCashier, String name, String phoneNumber, String address) {
        this.idCashier = idCashier;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
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

    public Boolean getResign() {
        return isResign;
    }

    public void setResign(Boolean resign) {
        isResign = resign;
    }

    public Date getResignedAt() {
        return resignedAt;
    }

    public void setResignedAt(Date resignedAt) {
        this.resignedAt = resignedAt;
    }
}
