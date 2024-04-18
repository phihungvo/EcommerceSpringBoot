package com.example.Ecommerce_Project.repository;

import com.example.Ecommerce_Project.models.CategoryEnum;
import com.example.Ecommerce_Project.models.Product;
import com.example.Ecommerce_Project.models.ProductDTO;
import com.example.Ecommerce_Project.models.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {

    @Query("SELECT new com.example.models.ProductDTO(p.productName, p.manufacturer, p.price, p.quantity)"
                + "FROM Product p WHERE p.category =:catenum")
    public List<ProductDTO> getAllProductsInACategory(@Param("catenum")CategoryEnum catenum);


    @Query("SELECT new com.example.models.ProductDTO(p.productName, p.manufacturer, p.price, p.quantity)"
           + "FROM Product p WHERE p.status =: status")
    public List<ProductDTO> getProductsWithStatus(@Param("status")ProductStatus status);

    @Query("SELECT new com.example.models.ProductDTO(p.productName, p.manufacturer, p.price, p.quantity)"
            + "FROM Product p WHERE p.seller.sellerId =: id"
    )
    public List<ProductDTO> getProductsOfASeller(@Param("id") Integer id);











}
