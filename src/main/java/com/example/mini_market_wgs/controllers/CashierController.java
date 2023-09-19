package com.example.mini_market_wgs.controllers;

import com.example.mini_market_wgs.dto.requests.DtoCashierRequest;
import com.example.mini_market_wgs.models.ApiResponse;
import com.example.mini_market_wgs.services.CashierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cashiers")
public class CashierController {
    @Autowired
    private CashierService cashierService;

    @GetMapping("")
    public ResponseEntity getCashiers(@RequestParam int page, @RequestParam int limit) {
        return ResponseEntity.status(HttpStatus.OK).body(cashierService.getAll(page, limit));
    }

    @GetMapping("/{idCashier}")
    public ResponseEntity getCashiers(@PathVariable String idCashier) {
        return ResponseEntity.status(HttpStatus.OK).body(cashierService.getByIdCashier(idCashier));
    }

    @PostMapping("")
    public ResponseEntity addCashier(@RequestBody DtoCashierRequest cashierRequest) {
        ApiResponse apiResponse = cashierService.add(cashierRequest);

        if (apiResponse.getData() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }
    }

    @PutMapping("")
    public ResponseEntity updateDataCashier(@RequestBody DtoCashierRequest cashierRequest) {
        ApiResponse apiResponse = cashierService.updateDate(cashierRequest);

        if (apiResponse.getData() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }
    }

    @PatchMapping("")
    public ResponseEntity updateStatusCashier(@RequestBody DtoCashierRequest cashierRequest) {
        ApiResponse apiResponse = cashierService.updateStatusResign(cashierRequest.getIdCashier());

        if (apiResponse.getData() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }
    }

    @DeleteMapping("")
    public ResponseEntity delete(@RequestBody DtoCashierRequest cashierRequest) {
        ApiResponse apiResponse = cashierService.delete(cashierRequest.getIdCashier());

        if (apiResponse.getData() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }
    }
}
