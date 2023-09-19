package com.example.mini_market_wgs.services;

import com.example.mini_market_wgs.dto.requests.DtoTransactionDetailRequest;
import com.example.mini_market_wgs.dto.requests.DtoTransactionRequest;
import com.example.mini_market_wgs.dto.responses.DtoItemResponse;
import com.example.mini_market_wgs.dto.responses.DtoTransactionResponse;
import com.example.mini_market_wgs.models.ApiResponse;
import com.example.mini_market_wgs.models.Cashier;
import com.example.mini_market_wgs.models.Customer;
import com.example.mini_market_wgs.models.Item;
import com.example.mini_market_wgs.models.ItemRelational;
import com.example.mini_market_wgs.models.Transaction;
import com.example.mini_market_wgs.models.TransactionDetail;
import com.example.mini_market_wgs.repositories.CashierRepository;
import com.example.mini_market_wgs.repositories.CustomerRepository;
import com.example.mini_market_wgs.repositories.ItemRelationRepository;
import com.example.mini_market_wgs.repositories.ItemRepository;
import com.example.mini_market_wgs.repositories.TransactionRepository;
import com.example.mini_market_wgs.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CashierRepository cashierRepository;
    @Autowired
    private ItemRelationRepository itemRelationRepository;
    @Autowired
    private ItemRepository itemRepository;

    public ApiResponse add(DtoTransactionRequest transactionRequest) {
        if (transactionRequest.getIdCustomer() == null) {
            return new ApiResponse(Utility.message("customer_not_insert"));
        } else if (transactionRequest.getIdCashier() == null) {
            return new ApiResponse(Utility.message("cashier_not_insert"));
        } else if (transactionRequest.getTransactionDetailRequests() == null) {
            return new ApiResponse(Utility.message("item_not_insert"));
        } else if (transactionRequest.getTotalPayment() == null) {
            return new ApiResponse(Utility.message("payment_not_insert"));
        }

        Optional<Customer> customerOptional = customerRepository.findFirstByIsDeletedIsFalseAndIdCustomer(transactionRequest.getIdCustomer());
        Optional<Cashier> cashierOptional = cashierRepository.findFirstByIsDeletedIsFalseAndIdCashier(transactionRequest.getIdCashier());

        if (!customerOptional.isPresent()) {
            return new ApiResponse("customer_invalid");
        } else if (!cashierOptional.isPresent()) {
            return new ApiResponse("cashier_invalid");
        } else if (transactionRequest.getTotalPayment() < 0) {
            return new ApiResponse(Utility.message("payment_invalid"));
        }
        List<TransactionDetail> transactionDetailList = new ArrayList<>();
        Transaction transaction = new Transaction();
        int totalPaid = 0;

        for (DtoTransactionDetailRequest transactionDetailRequest : transactionRequest.getTransactionDetailRequests()) {
            if (transactionDetailRequest.getIdItem() == null) {
                return new ApiResponse(Utility.message("item_not_insert"));
            }
            Optional<Item> itemOptional = itemRepository.findFirstByIsDeletedIsFalseAndIdItem(transactionDetailRequest.getIdItem());

            if (!itemOptional.isPresent()) {
                return new ApiResponse(Utility.message("item_invalid"));
            } else if (transactionDetailRequest.getQuantity() == null ||
                    transactionDetailRequest.getQuantity() <= 0) {
                return new ApiResponse(Utility.message("quantity_invalid"));
            } else {
                TransactionDetail transactionDetail = new TransactionDetail();
                int price = itemOptional.get().getPrice();
                int quantity = transactionDetailRequest.getQuantity();

                transactionDetail.setTransactionCore(transaction);
                transactionDetail.setItem(itemOptional.get());
                transactionDetail.setPrice(price);
                transactionDetail.setQuantity(quantity);
                transactionDetail.setTotalPrice(price * quantity);
                totalPaid += price * quantity;
                transactionDetailList.add(transactionDetail);
                addRelational(transactionDetailList);
            }
        }

        if (totalPaid > transactionRequest.getTotalPayment()) {
            return new ApiResponse(Utility.message("insufficient_money"));
        }

        transaction.setIdTransaction(getNewId(cashierOptional.get().getIdCashier()));
        transaction.setCashier(cashierOptional.get());
        transaction.setCustomer(customerOptional.get());
        transaction.setTransactionDetailList(transactionDetailList);
        transaction.setTotalPaid(totalPaid);
        transaction.setTotalPayment(transactionRequest.getTotalPayment());
        transaction.setTotalReturned(transactionRequest.getTotalPayment() - totalPaid);
        transactionRepository.save(transaction);

        return new ApiResponse(
                Utility.message("success"),
                new DtoTransactionResponse(transaction)
        );
    }

    public ApiResponse getAll(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Transaction> result = transactionRepository.findAllByOrderByCreatedAt(pageable);
        List<DtoTransactionResponse> resultDto = new ArrayList<>();

        for (Transaction transaction: result.getContent()) {
            System.out.println("size : " + transaction.getTransactionDetailList().size());
            resultDto.add(new DtoTransactionResponse(transaction));
        }
        return new ApiResponse(
                Utility.message("success"),
                new PageImpl<>(resultDto, pageable, result.getTotalElements()));
    }

    public ApiResponse getByIdItem(String idTransaction) {
        if (idTransaction == null) {
            return new ApiResponse(Utility.message("transaction_not_insert"));
        }
        Optional<Transaction> transactionOptional = transactionRepository.findFirstByIdTransaction(idTransaction);

        if (!transactionOptional.isPresent()) {
            return new ApiResponse(Utility.message("item_invalid"));
        } else {
            return new ApiResponse(
                    Utility.message("success"),
                    new DtoTransactionResponse(transactionOptional.get()));
        }
    }

    private void addRelational(List<TransactionDetail> transactionDetailList) {
        for (int indexTransactionFirst = 0; indexTransactionFirst < transactionDetailList.size() - 1; indexTransactionFirst++) {
            for (int indexTransactionSecond = indexTransactionFirst + 1; indexTransactionSecond < transactionDetailList.size(); indexTransactionSecond++) {
                Item item1 = transactionDetailList.get(indexTransactionFirst).getItem();
                Item item2 = transactionDetailList.get(indexTransactionSecond).getItem();

                if (item1.getIdItem().compareTo(item2.getIdItem()) < 0) {
                    Optional<ItemRelational> itemRelationalOptional = itemRelationRepository.findFirstByItem1AndItem2(item1, item2);

                    if (itemRelationalOptional.isPresent()) {
                        itemRelationalOptional.get().addCount();
                        itemRelationRepository.save(itemRelationalOptional.get());
                    } else {
                        ItemRelational itemRelational = new ItemRelational();
                        itemRelational.setItem1(item1);
                        itemRelational.setItem2(item2);
                        itemRelational.setCount(1);
                        itemRelationRepository.save(itemRelational);
                    }
                } else if (item1.getIdItem().compareTo(item2.getIdItem()) > 0) {
                    Optional<ItemRelational> itemRelationalOptional = itemRelationRepository.findFirstByItem1AndItem2(item2, item1);

                    if (itemRelationalOptional.isPresent()) {
                        itemRelationalOptional.get().addCount();
                        itemRelationRepository.save(itemRelationalOptional.get());
                    } else {
                        ItemRelational itemRelational = new ItemRelational();
                        itemRelational.setItem1(item2);
                        itemRelational.setItem2(item1);
                        itemRelational.setCount(0);
                        itemRelationRepository.save(itemRelational);
                    }
                }
            }
        }
    }

    private String getNewId(String idCashier) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String date = format.format(new Timestamp(System.currentTimeMillis()));
        Optional<Transaction> transactionOptional = transactionRepository.findFirstByIdTransactionContainingOrderByIdTransactionDesc(date + idCashier);
        int count = 1;

        if (transactionOptional.isPresent()) {
            String idTransaction = transactionOptional.get().getIdTransaction();
            count = Integer.parseInt(idTransaction.substring(11)) + 1;
        }

        return String.format("%s%s%03d", date, idCashier, count);
    }
}
