package com.studies.productservice.dto;

import com.studies.productservice.model.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductList {
    private long count;
    private List<Product> products;
}
