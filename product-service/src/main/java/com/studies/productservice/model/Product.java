package com.studies.productservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    @JsonIgnore
    private String createdAt;
    @JsonIgnore
    private String updatedAt;
}
