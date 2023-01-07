package com.studies.productservice.service;

import com.studies.productservice.dto.ProductList;
import com.studies.productservice.dto.ProductRequest;
import com.studies.productservice.model.Product;
import com.studies.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .createdAt(String.valueOf(System.currentTimeMillis()))
                .updatedAt(String.valueOf(System.currentTimeMillis()))
                .build();

        productRepository.save(product);
        log.info("Product {} is saved",product.getId());
    }

    public ProductList getAllProducts() {
        long count = productRepository.count();
        List<Product> products = productRepository.findAll();
        ProductList productList = new ProductList();
        productList.setCount(count);
        productList.setProducts(products);
        return productList;
    }
}
