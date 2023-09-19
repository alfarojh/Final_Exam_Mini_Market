package com.example.mini_market_wgs.dto.responses;

import com.example.mini_market_wgs.models.Transaction;
import com.example.mini_market_wgs.models.TransactionDetail;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DtoTransactionResponse {
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createdAt;
    @JsonProperty("id_transaction")
    private String idTransaction;
    @JsonProperty("name_customer")
    private String nameCustomer;
    @JsonProperty("name_cashier")
    private String nameCashier;
    @JsonProperty("item_list")
    private List<DtoTransactionDetailResponse> transactionDetailResponses;
    @JsonProperty("total_payment")
    private Integer totalPayment;
    @JsonProperty("total_paid")
    private Integer totalPaid;
    @JsonProperty("total_returned")
    private Integer totalReturned;

    public DtoTransactionResponse(Transaction transaction) {
        this.idTransaction = transaction.getIdTransaction();
        this.nameCustomer = transaction.getCustomer().getName();
        this.nameCashier = transaction.getCashier().getName();
        this.totalPayment = transaction.getTotalPayment();
        this.totalPaid = transaction.getTotalPaid();
        this.totalReturned = transaction.getTotalReturned();
        this.transactionDetailResponses = new ArrayList<>();
        for (TransactionDetail transactionDetail : transaction.getTransactionDetailList()) {
            transactionDetailResponses.add(new DtoTransactionDetailResponse(transactionDetail));
        }
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getNameCashier() {
        return nameCashier;
    }

    public void setNameCashier(String nameCashier) {
        this.nameCashier = nameCashier;
    }

    public List<DtoTransactionDetailResponse> getTransactionDetailResponses() {
        return transactionDetailResponses;
    }

    public void setTransactionDetailResponses(List<DtoTransactionDetailResponse> transactionDetailResponses) {
        this.transactionDetailResponses = transactionDetailResponses;
    }

    public Integer getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Integer totalPayment) {
        this.totalPayment = totalPayment;
    }

    public Integer getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(Integer totalPaid) {
        this.totalPaid = totalPaid;
    }

    public Integer getTotalReturned() {
        return totalReturned;
    }

    public void setTotalReturned(Integer totalReturned) {
        this.totalReturned = totalReturned;
    }
}
