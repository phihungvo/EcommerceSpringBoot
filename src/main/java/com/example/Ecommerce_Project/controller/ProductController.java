package com.example.Ecommerce_Project.controller;

import com.example.Ecommerce_Project.models.CategoryEnum;
import com.example.Ecommerce_Project.models.Product;
import com.example.Ecommerce_Project.models.ProductDTO;
import com.example.Ecommerce_Project.models.ProductStatus;
import com.example.Ecommerce_Project.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    // this method adds new product to catalog by seller(if seller is new it adds
    // seller as well
    // if seller is already existing products will be mapped to same seller) and
    // returns added product

    @PostMapping("/products")
    public ResponseEntity<Product> addProductToCataLogHandler(@RequestHeader("token")String token,
                                                              @Valid @RequestBody Product product){

        Product product1 = productService.addProductToCatalog(token, product);

        return new ResponseEntity<Product>(product1, HttpStatus.ACCEPTED);
    }



    // This method gets the product which needs to be added to the cart returns
    // product
    @GetMapping("product/{id}")
    public ResponseEntity<Product> getProductFromCataLogByIdHandler(@PathVariable("id")Integer id){

        Product product = productService.getProductFromCatalogById(id);

        return new ResponseEntity<Product>(product, HttpStatus.FOUND);
    }



    // This method will delete the product from catalog and returns the response
    // This will be called only when the product qty will be zero or seller wants to
    // delete for any other reason
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProductFromCatelogHandler(@PathVariable("id")Integer id){

        String res = productService.deleteProductFromCatelog(id);
        return new ResponseEntity<String>(res, HttpStatus.OK);
    }

    @PutMapping("/products")
    public ResponseEntity<Product> updateProductInCatalogHandler(@Valid @RequestBody Product product){

        Product product1 = productService.updateProductIncatalog(product);

        return  new ResponseEntity<Product>(product1, HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProductsHandler(){

        List<Product> list = productService.getAllProductInCatalog();

        return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
    }



    //this method gets the products mapped to a particular seller
    @GetMapping("/products/seller/{id}")
    public ResponseEntity<List<ProductDTO>> getAllProductsOfSellerHander(@PathVariable("id")Integer id){

        List<ProductDTO> list = productService.getAllProductsOfSeller(id);

        return new ResponseEntity<List<ProductDTO>>(list, HttpStatus.OK);
    }



    @GetMapping("/products/{catenum}")
    public ResponseEntity<List<ProductDTO>> getAllProductsInCategory(@PathVariable("catenum")String catenum){

        CategoryEnum ce = CategoryEnum.valueOf(catenum.toUpperCase());

        List<ProductDTO> list = productService.getProductOfCategory(ce);

        return new ResponseEntity<List<ProductDTO>>(list, HttpStatus.OK);
    }




    @GetMapping("/products/status/{status}")
    public ResponseEntity<List<ProductDTO>> getProductsWithStatusHandler(@PathVariable("status")String status){

        ProductStatus ps = ProductStatus.valueOf(status.toUpperCase());

        List<ProductDTO> list = productService.getProductOfStatus(ps);

        return new ResponseEntity<List<ProductDTO>>(list, HttpStatus.OK);
    }



    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateQuantityOfProduct(@PathVariable("id")Integer id, @RequestBody ProductDTO productDTO){

        Product product = productService.updateProductQuantityWithId(id, productDTO);

        return new ResponseEntity<Product>(product, HttpStatus.ACCEPTED);
    }

}
























