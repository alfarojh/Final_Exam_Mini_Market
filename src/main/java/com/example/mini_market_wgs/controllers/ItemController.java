package com.example.mini_market_wgs.controllers;

import com.example.mini_market_wgs.dto.requests.DtoCustomerRequest;
import com.example.mini_market_wgs.dto.requests.DtoItemRequest;
import com.example.mini_market_wgs.models.ApiResponse;
import com.example.mini_market_wgs.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("")
    public ResponseEntity getItems(@RequestParam int page, @RequestParam int limit) {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getAll(page, limit));
    }

    @GetMapping("/top-3")
    public ResponseEntity getItemsTop3() {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getTop3());
    }

    @GetMapping("/{idItem}")
    public ResponseEntity getItemByIdItem(@PathVariable String idItem) {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getByIdItem(idItem));
    }

    @PostMapping("")
    public ResponseEntity addItem(@RequestBody DtoItemRequest itemRequest) {
        ApiResponse apiResponse = itemService.add(itemRequest);

        if (apiResponse.getData() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }
    }

    @PutMapping("")
    public ResponseEntity updateDataItem(@RequestBody DtoItemRequest itemRequest) {
        ApiResponse apiResponse = itemService.updateData(itemRequest);

        if (apiResponse.getData() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }
    }

    @DeleteMapping("")
    public ResponseEntity deleteItem(@RequestBody DtoItemRequest itemRequest) {
        ApiResponse apiResponse = itemService.delete(itemRequest.getIdItem());

        if (apiResponse.getData() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }
    }
}
