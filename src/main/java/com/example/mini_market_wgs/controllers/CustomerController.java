package com.example.mini_market_wgs.controllers;

import com.example.mini_market_wgs.dto.requests.DtoCustomerRequest;
import com.example.mini_market_wgs.models.ApiResponse;
import com.example.mini_market_wgs.services.CustomerService;
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
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    // API untuk menampilkan daftar customer berdasarkan request.
    @GetMapping("")
    public ResponseEntity getCustomers(
            @RequestParam(required = false) String name,
            @RequestParam int page, @RequestParam int limit) {
        if (name != null) {
            return ResponseEntity.status(HttpStatus.OK).body(customerService.getAllByName(name, page, limit));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(customerService.getAll(page, limit));
        }
    }

    // API untuk menampilkan informasi kasir berdasarkan ID Customer.
    @GetMapping("/{idCustomer}")
    public ResponseEntity getCustomerByIdCustomer(@PathVariable String idCustomer) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getByIdCustomer(idCustomer));
    }

    // API untuk membuat customer baru berdasarkan request.
    @PostMapping("")
    public ResponseEntity addCustomer(@RequestBody DtoCustomerRequest customerRequest) {
        ApiResponse apiResponse = customerService.add(customerRequest);

        if (apiResponse.getData() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }
    }

    // API untuk memperbarui informasi customer berdasarkan request.
    @PutMapping("")
    public ResponseEntity updateDataCustomer(@RequestBody DtoCustomerRequest customerRequest) {
        ApiResponse apiResponse = customerService.updateData(customerRequest);

        if (apiResponse.getData() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }
    }

    // API untuk menghapus customer berdasarkan ID Customer.
    @DeleteMapping("")
    public ResponseEntity delete(@RequestBody DtoCustomerRequest customerRequest) {
        ApiResponse apiResponse = customerService.deleteIdCustomer(customerRequest.getIdCustomer());

        if (apiResponse.getData() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }
    }
}
