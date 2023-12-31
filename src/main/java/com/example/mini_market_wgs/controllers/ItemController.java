package com.example.mini_market_wgs.controllers;

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

    // API untuk menampilkan daftar barang berdasarkan request.
    @GetMapping("")
    public ResponseEntity getItems(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, name = "start_price") Integer startPrice,
            @RequestParam(required = false, name = "end_price") Integer endPrice,
            @RequestParam int page, @RequestParam int limit) {
        if (name != null) {
            return ResponseEntity.status(HttpStatus.OK).body(itemService.getAllByName(name, page, limit));
        } else if (startPrice != null && endPrice != null) {
            return ResponseEntity.status(HttpStatus.OK).body(itemService.getAllByPrice(startPrice, endPrice, page, limit));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(itemService.getAll(page, limit));
        }
    }

    // API untuk menampilkan daftar barang top berdasarkan request.
    @GetMapping("/top-item")
    public ResponseEntity getItemsTop(
            @RequestParam(required = false, name = "is_top3_relational") Boolean isTop,
            @RequestParam(required = false, name = "sku") String idItem) {
        if (isTop != null && isTop) {
            return ResponseEntity.status(HttpStatus.OK).body(itemService.getTopItemRelational());
        } else if (idItem != null) {
            return ResponseEntity.status(HttpStatus.OK).body(itemService.getTopItemRelationalByIdItem(idItem));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(itemService.getTop3());
        }
    }

    // API untuk menampilkan informasi barang berdasarkan ID barang.
    @GetMapping("/{idItem}")
    public ResponseEntity getItemByIdItem(@PathVariable String idItem) {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getByIdItem(idItem));
    }

    // API untuk membuat barang baru berdasarkan request.
    @PostMapping("")
    public ResponseEntity addItem(@RequestBody DtoItemRequest itemRequest) {
        ApiResponse apiResponse = itemService.add(itemRequest);

        if (apiResponse.getData() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }
    }

    // API untuk memperbaru informasi barang berdasarkan request.
    @PutMapping("")
    public ResponseEntity updateDataItem(@RequestBody DtoItemRequest itemRequest) {
        ApiResponse apiResponse = itemService.updateData(itemRequest);

        if (apiResponse.getData() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }
    }

    // API untuk menghapus barang berdasarkan ID Barang.
    @DeleteMapping("")
    public ResponseEntity deleteItem(@RequestBody DtoItemRequest itemRequest) {
        ApiResponse apiResponse = itemService.deleteByIdItem(itemRequest.getIdItem());

        if (apiResponse.getData() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }
    }
}
