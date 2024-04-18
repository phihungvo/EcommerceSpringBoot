package com.example.Ecommerce_Project.service;

import com.example.Ecommerce_Project.models.CategoryEnum;
import com.example.Ecommerce_Project.models.Product;
import com.example.Ecommerce_Project.models.ProductDTO;
import com.example.Ecommerce_Project.models.ProductStatus;

import java.util.List;

public interface ProductService {
    public Product addProductToCatalog(String token, Product product);

    public Product getProductFromCatalogById(Integer id);

    public String deleteProductFromCatelog(Integer id);

    public Product updateProductIncatalog(Product product);

    public List<Product> getAllProductInCatalog();

    public List<ProductDTO> getAllProductsOfSeller(Integer id);

    public List<ProductDTO> getProductOfCategory(CategoryEnum catenum);

    public List<ProductDTO> getProductOfStatus(ProductStatus status);

    public Product updateProductQuantityWithId(Integer id, ProductDTO prodDTO);

}

























