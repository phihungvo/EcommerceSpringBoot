package com.example.Ecommerce_Project.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private String prodName;
    private String manufacturer;
    private Double price;
    private Integer quantity;
}
