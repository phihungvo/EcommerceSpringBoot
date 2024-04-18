package com.example.Ecommerce_Project.service;

import com.example.Ecommerce_Project.exception.CategoryNotFoundException;
import com.example.Ecommerce_Project.exception.ProductNotFoundException;
import com.example.Ecommerce_Project.models.*;
import com.example.Ecommerce_Project.repository.ProductDao;
import com.example.Ecommerce_Project.repository.SellerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductDao productDao;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private SellerDao sellerDao;


    @Override
    public Product addProductToCatalog(String token, Product product) {

        Product prod = null;
        Seller seller = sellerService.getCurrentlyLoggedInSeller(token);
        product.setSeller(seller);

        Seller existingSeller = sellerService.getSellerByMobile(product.getSeller().getMobile(), token);
        Optional<Seller> opt = sellerDao.findById(existingSeller.getSellerId());

        if(opt.isPresent()){
            Seller seller1 = opt.get();

            product.setSeller(seller1);

            prod = productDao.save(product);

            seller1.getProduct().add(product);
            sellerDao.save(seller);
        }else{
            prod = productDao.save(product);
        }
        return prod;
    }

    @Override
    public Product getProductFromCatalogById(Integer id) {

        Optional<Product> opt = productDao.findById(id);

        if(opt.isPresent()){
            return opt.get();
        }
        else{
            throw new ProductNotFoundException("Product not found with given id");
        }
    }

    @Override
    public String deleteProductFromCatelog(Integer id) {

        Optional<Product> opt = productDao.findById(id);

        if(opt.isPresent()){
            Product prod = opt.get();
            System.out.println(prod);
            productDao.delete(prod);
            return "Product deleted from catalog";
        }else{
            throw new ProductNotFoundException("Product not found with given id");
        }
    }

    @Override
    public Product updateProductIncatalog(Product product) {
        Optional<Product> opt = productDao.findById(product.getProductId());

        if (opt.isPresent()) {
            opt.get();
            Product prod1 = productDao.save(product);
            return prod1;
        } else
            throw new ProductNotFoundException("Product not found with given id");
    }

    @Override
    public List<Product> getAllProductInCatalog() {

        List<Product> list = productDao.findAll();

        if(list.size() > 0)
        {
            return list;
        }else{
            throw new ProductNotFoundException("No products in catalog");
        }
    }

    @Override
    public List<ProductDTO> getAllProductsOfSeller(Integer id) {

        List<ProductDTO> list = productDao.getProductsOfASeller(id);

        if(list.size() > 0){
            return list;
        }else{
            throw new ProductNotFoundException("No product with seller id: "+id);
        }
    }

    @Override
    public List<ProductDTO> getProductOfCategory(CategoryEnum catenum) {

        List<ProductDTO> list = productDao.getAllProductsInACategory(catenum);

        if(list.size() > 0){
            return list;
        }else{
            throw new CategoryNotFoundException("No products found with category:" + catenum);
        }
    }



    @Override
    public List<ProductDTO> getProductOfStatus(ProductStatus status) {

        List<ProductDTO> list = productDao.getProductsWithStatus(status);

        if(list.size() > 0){
            return list;
        }else{
            throw new ProductNotFoundException("No products found with given status:" + status);
        }
    }

    @Override
    public Product updateProductQuantityWithId(Integer id, ProductDTO prodDTO) {

        Product prod = null;
        Optional<Product> opt = productDao.findById(id);

        if(opt != null){
            prod = opt.get();
            prod.setQuantity(prod.getQuantity() + prodDTO.getQuantity());

            if(prod.getQuantity() > 0){
                prod.setStatus(ProductStatus.AVAILABLE);
            }
            productDao.save(prod);
        }else {
            throw new ProductNotFoundException("No product found with this Id");
        }
        return prod;
    }
}




















