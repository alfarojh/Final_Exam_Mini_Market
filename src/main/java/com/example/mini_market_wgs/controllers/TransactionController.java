package com.example.mini_market_wgs.controllers;

import com.example.mini_market_wgs.dto.requests.DtoItemRequest;
import com.example.mini_market_wgs.dto.requests.DtoTransactionRequest;
import com.example.mini_market_wgs.models.ApiResponse;
import com.example.mini_market_wgs.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("")
    public ResponseEntity getItems(
            @RequestParam(required = false, name = "start_date") String startDate,
            @RequestParam(required = false, name = "end_date") String endDate,
            @RequestParam int page, @RequestParam int limit) {
        if (startDate != null && endDate != null) {
            return ResponseEntity.status(HttpStatus.OK).body(transactionService.getAllByDate(page, limit, startDate, endDate));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(transactionService.getAll(page, limit));
        }
    }

    @GetMapping("/{idTransaction}")
    public ResponseEntity getItemsByIdTransaction(@PathVariable String idTransaction) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.getByIdItem(idTransaction));
    }

    @PostMapping("")
    public ResponseEntity addTransaction(@RequestBody DtoTransactionRequest transactionRequest) {
        ApiResponse apiResponse = transactionService.add(transactionRequest);

        if (apiResponse.getData() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }
    }
}
