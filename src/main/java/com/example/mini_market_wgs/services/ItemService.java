package com.example.mini_market_wgs.services;

import com.example.mini_market_wgs.dto.requests.DtoItemRequest;
import com.example.mini_market_wgs.dto.responses.DtoItemRelationalResponse;
import com.example.mini_market_wgs.dto.responses.DtoItemResponse;
import com.example.mini_market_wgs.models.ApiResponse;
import com.example.mini_market_wgs.models.Item;
import com.example.mini_market_wgs.models.ItemRelational;
import com.example.mini_market_wgs.repositories.ItemRelationRepository;
import com.example.mini_market_wgs.repositories.ItemRepository;
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
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemRelationRepository itemRelationRepository;

    public ApiResponse add(DtoItemRequest itemRequest) {
        if (Utility.isNotAlphanumeric(itemRequest.getName())) {
            return new ApiResponse(Utility.message("name_invalid"));
        } else if (itemRequest.getPrice() == null || itemRequest.getPrice() < 0) {
            return new ApiResponse(Utility.message("price_invalid"));
        } else {
            Item item = new Item();

            item.setIdItem(getNewId(itemRequest.getName()));
            item.setName(itemRequest.getName());
            item.setPrice(itemRequest.getPrice());
            itemRepository.save(item);

            return new ApiResponse(
                    Utility.message("success"),
                    new DtoItemResponse(item)
            );
        }
    }

    public ApiResponse updateData(DtoItemRequest itemRequest) {
        if (itemRequest.getIdItem() == null) {
            return new ApiResponse(Utility.message("item_not_insert"));
        }
        Optional<Item> itemOptional = itemRepository.findFirstByIsDeletedIsFalseAndIdItem(itemRequest.getIdItem());

        if (!itemOptional.isPresent()) {
            return new ApiResponse(Utility.message("item_invalid"));
        } else if (itemRequest.getPrice() == null || itemRequest.getPrice() < 0) {
            return new ApiResponse(Utility.message("price_invalid"));
        } else {
            itemOptional.get().setPrice(itemRequest.getPrice());
            itemRepository.save(itemOptional.get());

            return new ApiResponse(
                    Utility.message("success"),
                    new DtoItemResponse(itemOptional.get())
            );
        }
    }

    public ApiResponse delete(String idItem) {
        if (idItem == null) {
            return new ApiResponse(Utility.message("item_not_insert"));
        }
        Optional<Item> itemOptional = itemRepository.findFirstByIsDeletedIsFalseAndIdItem(idItem);

        if (!itemOptional.isPresent()) {
            return new ApiResponse(Utility.message("item_invalid"));
        } else {
            itemOptional.get().setDeleted(true);
            itemOptional.get().setDeletedAt(new Date());
            itemRepository.save(itemOptional.get());

            return new ApiResponse(
                    Utility.message("success"),
                    new DtoItemResponse(itemOptional.get())
            );
        }
    }

    public ApiResponse getAll(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Item> result = itemRepository.findAllByIsDeletedIsFalseOrderByName(pageable);
        List<DtoItemResponse> resultDto = new ArrayList<>();

        for (Item item: result.getContent()) {
            resultDto.add(new DtoItemResponse(item));
        }
        return new ApiResponse(
                Utility.message("success"),
                new PageImpl<>(resultDto, pageable, result.getTotalElements()));
    }

    public ApiResponse getTop3() {
        List<Item> itemList = itemRepository.findTop3ByOrderByQuantityPurchasedDesc();
        List<DtoItemResponse> resultDto = new ArrayList<>();

        for (Item item: itemList) {
            resultDto.add(new DtoItemResponse(item));
        }
        return new ApiResponse(
                Utility.message("success"),
                resultDto);

    }

    public ApiResponse getTopItemRelational() {
        List<DtoItemRelationalResponse> itemList = new ArrayList<>();

        for (ItemRelational itemRelational : itemRelationRepository.findTop3ByOrderByCountDesc()) {
            itemList.add(new DtoItemRelationalResponse(itemRelational));
        }
        return new ApiResponse(
                Utility.message("success"),
                itemList);
    }

    public ApiResponse getTopItemRelationalByIdItem(String idItem) {
        List<DtoItemRelationalResponse> itemList = new ArrayList<>();

        for (ItemRelational itemRelational : itemRelationRepository.findTop3ByItem1_IdItemOrItem2_IdItemOrderByCountDesc(idItem, idItem)) {
            itemList.add(new DtoItemRelationalResponse(itemRelational));
        }
        return new ApiResponse(
                Utility.message("success"),
                itemList);
    }

    public ApiResponse getByIdItem(String idItem) {
        if (idItem == null) {
            return new ApiResponse(Utility.message("item_not_insert"));
        }
        Optional<Item> itemOptional = itemRepository.findFirstByIsDeletedIsFalseAndIdItem(idItem);

        if (!itemOptional.isPresent()) {
            return new ApiResponse(Utility.message("item_invalid"));
        } else {
            return new ApiResponse(
                    Utility.message("success"),
                    new DtoItemResponse(itemOptional.get()));
        }
    }

    private String getNewId(String name) {
        String codeItem = name.substring(0, 1).toUpperCase();
        Optional<Item> itemOptional = itemRepository.findFirstByIdItemContainingOrderByIdItemDesc(codeItem);
        int count = 1;

        if (itemOptional.isPresent()) {
            count = Integer.parseInt(itemOptional.get().getIdItem().substring(1)) + 1;
            System.out.println("hai " + count);
        }
        return String.format("%s%05d", codeItem, count);
    }
}
