package com.example.mini_market_wgs.services;

import com.example.mini_market_wgs.dto.requests.DtoTransactionDetailRequest;
import com.example.mini_market_wgs.dto.requests.DtoTransactionRequest;
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
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
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

    // Fungsi untuk menambahkan transaksi baru.
    @Transactional
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

        // Validasi item transaksi.
        for (DtoTransactionDetailRequest transactionDetailRequest : transactionRequest.getTransactionDetailRequests()) {
            if (transactionDetailRequest.getIdItem() == null) {
                return new ApiResponse(Utility.message("item_not_insert"));
            }
            Optional<Item> itemOptional = itemRepository.findFirstByIsDeletedIsFalseAndIdItem(transactionDetailRequest.getIdItem());

            if (!itemOptional.isPresent()) {
                return new ApiResponse(Utility.message("item_invalid"));
            } else if (transactionDetailRequest.getQuantity() == null || transactionDetailRequest.getQuantity() <= 0) {
                return new ApiResponse(Utility.message("quantity_invalid"));
            } else {
                TransactionDetail transactionDetail = new TransactionDetail();
                int price = itemOptional.get().getPrice();
                int quantity = transactionDetailRequest.getQuantity();

                itemOptional.get().addQuantityPurchased(quantity);
                transactionDetail.setTransactionCore(transaction);
                transactionDetail.setItem(itemOptional.get());
                transactionDetail.setPrice(price);
                transactionDetail.setQuantity(quantity);
                transactionDetail.setTotalPrice(price * quantity);
                totalPaid += price * quantity;
                transactionDetailList.add(transactionDetail);
            }
        }

        // Validasi pembayaran dan tanggal.
        Date date;
        if (totalPaid > transactionRequest.getTotalPayment()) {
            return new ApiResponse(Utility.message("insufficient_money", String.valueOf(totalPaid)));
        } else if (transactionRequest.getTransactionDate() == null) {
            System.out.println("Date null");
            date = new Date();
        } else {
            date = Date.from(Instant.parse(transactionRequest.getTransactionDate() + "T00:00:00Z"));
        }

        addRelational(transactionDetailList);
        transaction.setTransactionDate(date);
        transaction.setIdTransaction(getNewId(cashierOptional.get().getIdCashier(), date));
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

    // Fungsi untuk mencatat barang yang sering dibeli bersamaan.
    private void addRelational(List<TransactionDetail> transactionDetailList) {
        List<Item> distinctItemList = distinctListTransactionalDetail(transactionDetailList);

        for (int indexTransactionFirst = 0; indexTransactionFirst < distinctItemList.size() - 1; indexTransactionFirst++) {
            Item item1 = distinctItemList.get(indexTransactionFirst);

            for (int indexTransactionSecond = indexTransactionFirst + 1; indexTransactionSecond < distinctItemList.size(); indexTransactionSecond++) {
                Item item2 = distinctItemList.get(indexTransactionSecond);

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
                        itemRelational.setCount(1);
                        itemRelationRepository.save(itemRelational);
                    }
                }
            }
        }
    }

    // Fungsi untuk menampilkan daftar transaksi.
    public ApiResponse getAll(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return convertToDto(transactionRepository.findAllByOrderByTransactionDateDesc(pageable), pageable);
    }

    // Fungsi untuk menampilkan daftar transaksi berdasarkan tanggal dan customer.
    public ApiResponse getAllByDate(int page, int limit, String startDate, String endDate, String idCustomer) {
        Date formatStartDate = Date.from(Instant.parse(startDate + "T00:00:00Z"));
        Date formatEndDate = Date.from(Instant.parse(endDate + "T00:00:00Z"));

        Pageable pageable = PageRequest.of(page, limit);
        Page<Transaction> result = idCustomer != null ?
                transactionRepository.findAllByTransactionDateBetweenAndCustomer_IdCustomerOrderByTransactionDateDesc(formatStartDate, formatEndDate, idCustomer, pageable) :
                transactionRepository.findAllByTransactionDateBetweenOrderByTransactionDateDesc(formatStartDate, formatEndDate, pageable);

        return convertToDto(result, pageable);
    }

    private ApiResponse convertToDto(Page<Transaction> transactionPage, Pageable pageable) {
        List<DtoTransactionResponse> resultDto = new ArrayList<>();

        for (Transaction transaction : transactionPage.getContent()) {
            resultDto.add(new DtoTransactionResponse(transaction));
        }
        return new ApiResponse(
                Utility.message("success"),
                new PageImpl<>(resultDto, pageable, transactionPage.getTotalElements()));
    }

    // Fungsi untuk mendapatkan informasi transaksi berdasarkan ID Transaksi
    public ApiResponse getByIdItem(String idTransaction) {
        if (idTransaction == null) {
            return new ApiResponse(Utility.message("transaction_not_insert"));
        }
        Optional<Transaction> transactionOptional = transactionRepository.findFirstByIdTransaction(idTransaction);

        if (!transactionOptional.isPresent()) {
            return new ApiResponse(Utility.message("transaction_invalid"));
        } else {
            return new ApiResponse(
                    Utility.message("success"),
                    new DtoTransactionResponse(transactionOptional.get()));
        }
    }

    // Fungsi untuk membuat setiap barang yang dibeli oleh pelanggan bersifat unik,
    // bahkan jika pengguna membeli barang yang sama dalam satu transaksi.
    private List<Item> distinctListTransactionalDetail(List<TransactionDetail> transactionDetailList) {
        List<Item> distinctList = new ArrayList<>();

        for (TransactionDetail transactionDetail : transactionDetailList) {
            boolean isDistinct = true;
            int index = 0;

            while (isDistinct && index < distinctList.size()) {
                if (transactionDetail.getItem().getIdItem().equals(distinctList.get(index).getIdItem())) {
                    isDistinct = false;
                }
                index++;
            }
            if (isDistinct) {
                distinctList.add(transactionDetail.getItem());
            }
        }
        return distinctList;
    }

    // Fungsi untuk membuat ID Transaksi baru.
    private String getNewId(String idCashier, Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String formatDate = format.format(date);
        Optional<Transaction> transactionOptional = transactionRepository.findFirstByIdTransactionContainingOrderByIdTransactionDesc(formatDate + idCashier);
        int count = 1;

        if (transactionOptional.isPresent()) {
            String idTransaction = transactionOptional.get().getIdTransaction();
            count = Integer.parseInt(idTransaction.substring(11)) + 1;
        }

        return String.format("%s%s%03d", formatDate, idCashier, count);
    }
}
