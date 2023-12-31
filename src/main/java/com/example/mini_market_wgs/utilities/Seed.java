package com.example.mini_market_wgs.utilities;

import com.example.mini_market_wgs.dto.requests.DtoTransactionDetailRequest;
import com.example.mini_market_wgs.dto.requests.DtoTransactionRequest;
import com.example.mini_market_wgs.models.ApiResponse;
import com.example.mini_market_wgs.models.Cashier;
import com.example.mini_market_wgs.models.Customer;
import com.example.mini_market_wgs.models.Item;
import com.example.mini_market_wgs.repositories.CashierRepository;
import com.example.mini_market_wgs.repositories.CustomerRepository;
import com.example.mini_market_wgs.repositories.ItemRepository;
import com.example.mini_market_wgs.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class Seed {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CashierRepository cashierRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private TransactionService transactionService;

    private final List<Customer> customerList = new ArrayList<>();
    private final List<Cashier> cashierList = new ArrayList<>();
    private final List<Item> itemList = new ArrayList<>();

    @Transactional
    public ApiResponse allSeed() {
        if (customerRepository.count() == 0) {
            seedCustomer();
            seedCashier();
            seedItem();
            seedTransaction();
        }
        return new ApiResponse(Utility.message("success"));
    }

    @Transactional
    public void seedCustomer() {
        customerList.add(new Customer("201201020001", "Alice", "087479720895"));
        customerList.add(new Customer("201301010001", "Bob", "083094894366"));
        customerList.add(new Customer("201401010001", "Carol", "083094720908"));
        customerList.add(new Customer("201501010001", "David", "087710899362"));
        customerList.add(new Customer("201601010001", "Emily", "085725161834"));
        customerList.add(new Customer("201701010001", "Frank", "082965955892"));
        customerList.add(new Customer("201801010001", "Grace", "080340287522"));
        customerList.add(new Customer("201901010001", "Henry", "085841925417"));
        customerList.add(new Customer("202001010001", "Isabella", "088333813173"));
        customerList.add(new Customer("202101010001", "Jack", "082470122357"));
        customerRepository.saveAll(customerList);
    }

    @Transactional
    public void seedCashier() {
        cashierList.add(new Cashier("001", "Kate", "081123971600", "Bandung"));
        cashierList.add(new Cashier("002", "Liam", "082783009977", "Bandung"));
        cashierList.add(new Cashier("003", "Mia", "083355752814", "Bandung"));
        cashierList.add(new Cashier("004", "Noah", "084792863956", "Bandung"));
        cashierList.add(new Cashier("005", "Olivia", "083984972776", "Bandung"));
        cashierList.add(new Cashier("006", "Peter", "082188470974", "Bandung"));
        cashierList.add(new Cashier("007", "Quinn", "087653958522", "Bandung"));
        cashierList.add(new Cashier("008", "Rachel", "080757925537", "Bandung"));
        cashierList.add(new Cashier("009", "Samuel", "082529517984", "Bandung"));
        cashierList.add(new Cashier("010", "Taylor", "084869722073", "Bandung"));
        cashierRepository.saveAll(cashierList);
    }

    @Transactional
    public void seedItem() {
        itemList.add(new Item("L00001", "Leo Kripik Kentang Rumput Laut 14 gr", 1_000));
        itemList.add(new Item("L00002", "Leo Kripik Kentang Sapi Panggang 14 gr", 1_000));
        itemList.add(new Item("L00003", "Leo Kripik Kentang Ayam Original 14 gr", 1_000));
        itemList.add(new Item("L00004", "Leanet Beef BBQ 8 gr", 500));
        itemList.add(new Item("L00005", "Leanet Seaweed 8 gr", 500));
        itemList.add(new Item("P00001", "Piattoz Sapi Panggang 12 gr", 1_000));
        itemList.add(new Item("P00002", "Piattoz Barbeque 12 gr", 1_000));
        itemList.add(new Item("K00001", "Kentang Goreng ala French Fries 18 gr", 1_000));
        itemList.add(new Item("T00001", "Twistko Salsa rasa Balado", 1_000));
        itemList.add(new Item("K00002", "Kenji Net rasa Ayam 16 gr", 1_000));
        itemList.add(new Item("K00003", "Kenji Net rasa Dendeng 16 gr", 1_000));
        itemList.add(new Item("K00004", "Kenji Kari Ayam 10 gr", 500));
        itemList.add(new Item("K00005", "Kenji Sapi Panggang", 500));
        itemRepository.saveAll(itemList);
    }

    @Transactional
    public void seedTransaction() {
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            DtoTransactionRequest transactionRequest = new DtoTransactionRequest();
            List<DtoTransactionDetailRequest> transactionDetailRequestList = new ArrayList<>();
            for (int j = 0; j < random.nextInt(5) + 1; j++) {
                transactionDetailRequestList.add(new DtoTransactionDetailRequest(itemList.get(random.nextInt(itemList.size())).getIdItem(), random.nextInt(10) + 1));
            }
            int startDate = (int) LocalDate.of(2000, 1, 1).toEpochDay();
            int endDate = (int) LocalDate.of(2023, 1, 1).toEpochDay();
            LocalDate randomDate = LocalDate.ofEpochDay(startDate + random.nextInt(endDate - startDate));

            transactionRequest.setTransactionDate(String.valueOf(randomDate));
            transactionRequest.setIdCashier(cashierList.get(random.nextInt(cashierList.size())).getIdCashier());
            transactionRequest.setIdCustomer(customerList.get(random.nextInt(customerList.size())).getIdCustomer());
            transactionRequest.setTransactionDetailRequests(transactionDetailRequestList);
            transactionRequest.setTotalPayment((1 + random.nextInt(10)) * 100_000);
            transactionService.add(transactionRequest);
        }


    }
}
