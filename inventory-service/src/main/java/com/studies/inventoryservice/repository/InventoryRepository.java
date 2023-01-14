package com.studies.inventoryservice.repository;

import com.studies.inventoryservice.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, String> {
    @Query("{ 'skuCode' : { $in:  ?0  } }")
    List<Inventory> findBySkuCode(List<String> skuCode);
}
