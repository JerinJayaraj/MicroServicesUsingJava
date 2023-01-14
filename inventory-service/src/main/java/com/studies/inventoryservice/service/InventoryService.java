package com.studies.inventoryservice.service;

import com.studies.inventoryservice.dto.InventoryResponse;
import com.studies.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<InventoryResponse> isInStock(List<String> skuCode){
        List<InventoryResponse> response =  inventoryRepository.findBySkuCode(skuCode)
                .stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity() > 1)
                            .build()
                )
                .collect(Collectors.toList());
        return response;
    }
}
