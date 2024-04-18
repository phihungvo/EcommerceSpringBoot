package com.example.Ecommerce_Project.service;

import com.example.Ecommerce_Project.exception.ProductNotFoundException;
import com.example.Ecommerce_Project.models.CartDTO;
import com.example.Ecommerce_Project.models.CartItem;
import com.example.Ecommerce_Project.models.Product;
import com.example.Ecommerce_Project.models.ProductStatus;
import com.example.Ecommerce_Project.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService{

    @Autowired
    ProductDao productDao;

    @Override
    public CartItem createItemforCart(CartDTO cartDTO) {

        Product existingProduct = productDao.findById(cartDTO.getProductId()).orElseThrow(() -> new ProductNotFoundException("Product Not Found"));

        if(existingProduct.getStatus().equals(ProductStatus.OUTOSTOCK) || existingProduct.getQuantity() == 0){
            throw new ProductNotFoundException("Product OUT OF STOCK");
        }

        CartItem newItem = new CartItem();

        newItem.setCartItemQuantity(1);

        newItem.setCartProduct(existingProduct);

        return newItem;
    }



//	@Override
//	public CartItem addItemToCart(CartDTO cartdto) {
//
//		// TODO Auto-generated method stub
//
////		Product existingProduct = productDao.findById(cartdto.getProductId()).orElseThrow( () -> new ProductException("Product Not found"));
//
//		Optional<Product> opt = productDao.findById(cartdto.getProductId());
//
//		if(opt.isEmpty())
//			throw new ProductNotFoundException("Product not found");
//
//		Product existingProduct = opt.get();
//
//		CartItem newItem = new CartItem();
//
//		newItem.setCartProduct(existingProduct);
//
//		newItem.setCartItemQuantity(1);
//
//		return newItem;
//	}
}

















