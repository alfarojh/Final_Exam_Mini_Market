package com.example.mini_market_wgs.services;

import com.example.mini_market_wgs.dto.requests.DtoCustomerRequest;
import com.example.mini_market_wgs.dto.responses.DtoCustomerResponse;
import com.example.mini_market_wgs.models.ApiResponse;
import com.example.mini_market_wgs.models.Customer;
import com.example.mini_market_wgs.repositories.CustomerRepository;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public ApiResponse add(DtoCustomerRequest customerRequest) {
        if (Utility.isNotAlphanumeric(customerRequest.getName())) {
            return new ApiResponse(Utility.message("name_invalid"));
        } else if (Utility.isPhoneNumberNotValid(customerRequest.getPhoneNumber())) {
            return new ApiResponse(Utility.message("phone_invalid"));
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            int year = Integer.parseInt(format.format(new Timestamp(System.currentTimeMillis())));
            Customer customer = new Customer();

            customer.setIdCustomer(getNewId());
            customer.setName(customerRequest.getName());
            customer.setPhoneNumber(customerRequest.getPhoneNumber());
            customerRepository.save(customer);

            return new ApiResponse(
                    Utility.message("success"),
                    new DtoCustomerResponse(customer));
        }
    }

    public ApiResponse updateData(DtoCustomerRequest customerRequest) {
        if (customerRequest.getIdCustomer() == null) {
            return new ApiResponse(Utility.message("customer_not_insert"));
        }
        Optional<Customer> customerOptional = customerRepository.findFirstByIsDeletedIsFalseAndIdCustomer(customerRequest.getIdCustomer());

        if (!customerOptional.isPresent()) {
            return new ApiResponse(Utility.message("customer_invalid"));
        } else if (Utility.isNotAlphanumeric(customerRequest.getName())) {
            return new ApiResponse(Utility.message("name_invalid"));
        } else if (Utility.isPhoneNumberNotValid(customerRequest.getPhoneNumber())) {
            return new ApiResponse(Utility.message("phone_invalid"));
        } else {
            customerOptional.get().setName(customerRequest.getName());
            customerOptional.get().setPhoneNumber(customerRequest.getPhoneNumber());
            customerRepository.save(customerOptional.get());

            return new ApiResponse(
                    Utility.message("success"),
                    new DtoCustomerResponse(customerOptional.get()));
        }
    }

    public ApiResponse delete(String idCustomer) {
        if (idCustomer == null) {
            return new ApiResponse(Utility.message("customer_not_insert"));
        }
        Optional<Customer> customerOptional = customerRepository.findFirstByIsDeletedIsFalseAndIdCustomer(idCustomer);

        if (!customerOptional.isPresent()) {
            return new ApiResponse(Utility.message("customer_invalid"));
        } else {
            customerOptional.get().setDeleted(true);
            customerOptional.get().setDeletedAt(new Date());
            customerRepository.save(customerOptional.get());

            return new ApiResponse(
                    Utility.message("success"),
                    new DtoCustomerResponse(customerOptional.get()));
        }
    }

    public ApiResponse getAll(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return convertDto(customerRepository.findAllByIsDeletedIsFalseOrderByName(pageable), pageable);
    }

    public ApiResponse getAllByName(String name, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return convertDto(customerRepository.findAllByIsDeletedIsFalseAndNameContainingIgnoreCaseOrderByName(name, pageable), pageable);
    }

    private ApiResponse convertDto(Page<Customer> customerPage, Pageable pageable) {
        List<DtoCustomerResponse> resultDto = new ArrayList<>();

        for (Customer customer : customerPage.getContent()) {
            resultDto.add(new DtoCustomerResponse(customer));
        }
        return new ApiResponse(
                Utility.message("success"),
                new PageImpl<>(resultDto, pageable, customerPage.getTotalElements()));
    }

    public ApiResponse getByIdCustomer(String idCustomer) {
        if (idCustomer == null) {
            return new ApiResponse(Utility.message("customer_not_insert"));
        }
        Optional<Customer> customerOptional = customerRepository.findFirstByIsDeletedIsFalseAndIdCustomer(idCustomer);

        if (!customerOptional.isPresent()) {
            return new ApiResponse(Utility.message("customer_invalid"));
        } else {
            return new ApiResponse(
                    Utility.message("success"),
                    new DtoCustomerResponse(customerOptional.get()));
        }
    }

    private String getNewId() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String date = format.format(new Timestamp(System.currentTimeMillis()));
        Optional<Customer> customerOptional = customerRepository.findFirstByIdCustomerContainingOrderByIdCustomerDesc(date);
        int count = 1;

        if (customerOptional.isPresent()) {
            String idCustomer = customerOptional.get().getIdCustomer();
            count = Integer.parseInt(idCustomer.substring(8)) + 1;
        }

        return String.format("%s%04d", date, count);

    }
}
