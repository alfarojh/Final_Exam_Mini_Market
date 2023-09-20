package com.example.mini_market_wgs.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class DtoTransactionRequest {
    @JsonProperty("transaction_date")
    private Date transactionDate;
    @JsonProperty("id_transaction")
    private String idTransaction;
    @JsonProperty("id_customer")
    private String idCustomer;
    @JsonProperty("id_cashier")
    private String idCashier;
    @JsonProperty("total_payment")
    private Integer totalPayment;
    @JsonProperty("item_list")
    private List<DtoTransactionDetailRequest> transactionDetailRequests;

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getIdCashier() {
        return idCashier;
    }

    public void setIdCashier(String idCashier) {
        this.idCashier = idCashier;
    }

    public Integer getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Integer totalPayment) {
        this.totalPayment = totalPayment;
    }

    public List<DtoTransactionDetailRequest> getTransactionDetailRequests() {
        return transactionDetailRequests;
    }

    public void setTransactionDetailRequests(List<DtoTransactionDetailRequest> transactionDetailRequests) {
        this.transactionDetailRequests = transactionDetailRequests;
    }
}
