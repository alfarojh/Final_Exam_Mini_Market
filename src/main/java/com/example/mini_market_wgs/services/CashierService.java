package com.example.mini_market_wgs.services;

import com.example.mini_market_wgs.dto.requests.DtoCashierRequest;
import com.example.mini_market_wgs.dto.responses.DtoCashierResponse;
import com.example.mini_market_wgs.models.ApiResponse;
import com.example.mini_market_wgs.models.Cashier;
import com.example.mini_market_wgs.repositories.CashierRepository;
import com.example.mini_market_wgs.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CashierService {
    @Autowired
    private CashierRepository cashierRepository;

    public ApiResponse add(DtoCashierRequest cashierRequest) {
        if (Utility.isNotAlphanumeric(cashierRequest.getName())) {
            return new ApiResponse(Utility.message("name_invalid"));
        } else if (Utility.isPhoneNumberNotValid(cashierRequest.getPhoneNumber())) {
            return new ApiResponse(Utility.message("phone_invalid"));
        } else if (Utility.isNotAlphanumeric(cashierRequest.getAddress())) {
            return new ApiResponse(Utility.message("address_invalid"));
        } else {
            Cashier cashier = new Cashier();

            cashier.setName(cashierRequest.getName());
            cashier.setIdCashier(getNewId());
            cashier.setAddress(cashierRequest.getAddress());
            cashier.setPhoneNumber(cashierRequest.getPhoneNumber());
            cashierRepository.save(cashier);

            return new ApiResponse(
                    Utility.message("success"),
                    new DtoCashierResponse(cashier));
        }
    }

    public ApiResponse updateDate(DtoCashierRequest cashierRequest) {
        if (cashierRequest.getIdCashier() == null) {
            return new ApiResponse(Utility.message("cashier_not_insert"));
        }
        Optional<Cashier> cashierOptional = cashierRepository.findFirstByIsDeletedIsFalseAndIdCashier(cashierRequest.getIdCashier());

        if (!cashierOptional.isPresent() || cashierOptional.get().getResign()) {
            return new ApiResponse(Utility.message("cashier_invalid"));
        } else if (Utility.isNotAlphanumeric(cashierRequest.getName())) {
            return new ApiResponse(Utility.message("name_invalid"));
        } else if (Utility.isPhoneNumberNotValid(cashierRequest.getPhoneNumber())) {
            return new ApiResponse(Utility.message("phone_invalid"));
        } else if (Utility.isNotAlphanumeric(cashierRequest.getAddress())) {
            return new ApiResponse(Utility.message("address_invalid"));
        } else {
            cashierOptional.get().setIdCashier(cashierRequest.getIdCashier());
            cashierOptional.get().setName(cashierRequest.getName());
            cashierOptional.get().setAddress(cashierRequest.getAddress());
            cashierOptional.get().setPhoneNumber(cashierRequest.getPhoneNumber());
            cashierRepository.save(cashierOptional.get());

            return new ApiResponse(
                    Utility.message("success"),
                    new DtoCashierResponse(cashierOptional.get()));
        }
    }

    public ApiResponse updateStatusResign(String idCashier) {
        if (idCashier == null) {
            return new ApiResponse(Utility.message("cashier_not_insert"));
        }
        Optional<Cashier> cashierOptional = cashierRepository.findFirstByIsDeletedIsFalseAndIdCashier(idCashier);

        if (!cashierOptional.isPresent() || cashierOptional.get().getResign()) {
            return new ApiResponse(Utility.message("cashier_invalid"));
        } else {
            cashierOptional.get().setResign(true);
            cashierOptional.get().setResignedAt(new Date());
            cashierRepository.save(cashierOptional.get());

            return new ApiResponse(
                    Utility.message("success"),
                    new DtoCashierResponse(cashierOptional.get()));
        }
    }

    public ApiResponse delete(String idCashier) {
        if (idCashier == null) {
            return new ApiResponse(Utility.message("cashier_not_insert"));
        }
        Optional<Cashier> cashierOptional = cashierRepository.findFirstByIsDeletedIsFalseAndIdCashier(idCashier);

        if (!cashierOptional.isPresent()) {
            return new ApiResponse(Utility.message("cashier_invalid"));
        } else {
            cashierOptional.get().setDeleted(true);
            cashierOptional.get().setDeletedAt(new Date());
            cashierRepository.save(cashierOptional.get());

            return new ApiResponse(
                    Utility.message("success"),
                    new DtoCashierResponse(cashierOptional.get()));
        }
    }

    public ApiResponse getAll(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Cashier> result = cashierRepository.findAllByIsDeletedIsFalseOrderByName(pageable);
        List<DtoCashierResponse> resultDto = new ArrayList<>();

        for (Cashier cashier : result.getContent()) {
            resultDto.add(new DtoCashierResponse(cashier));
        }
        return new ApiResponse(
                Utility.message("success"),
                new PageImpl<>(resultDto, pageable, result.getTotalElements()));
    }

    public ApiResponse getByIdCashier(String idCashier) {
        if (idCashier == null) {
            return new ApiResponse(Utility.message("cashier_not_insert"));
        }
        Optional<Cashier> cashierOptional = cashierRepository.findFirstByIsDeletedIsFalseAndIdCashier(idCashier);

        if (!cashierOptional.isPresent()) {
            return new ApiResponse(Utility.message("cashier_invalid"));
        } else {
            return new ApiResponse(
                    Utility.message("success"),
                    new DtoCashierResponse(cashierOptional.get()));
        }
    }

    private String getNewId() {
        Optional<Cashier> cashierOptional = cashierRepository.findFirstByOrderByIdCashierDesc();
        int count = 1;

        if (cashierOptional.isPresent()) {
            count = Integer.parseInt(cashierOptional.get().getIdCashier()) + 1;
        }
        return String.format("%03d", count);
    }
}
