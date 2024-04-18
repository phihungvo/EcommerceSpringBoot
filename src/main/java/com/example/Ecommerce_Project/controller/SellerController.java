package com.example.Ecommerce_Project.controller;

import com.example.Ecommerce_Project.models.Seller;
import com.example.Ecommerce_Project.models.SellerDTO;
import com.example.Ecommerce_Project.models.SessionDTO;
import com.example.Ecommerce_Project.service.SellerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.backoff.Sleeper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SellerController {

    @Autowired
    private SellerService sellerService;



    //ADD NEW SELLER----------------
    @PostMapping("/addseller")
    public ResponseEntity<Seller> addSellerHandler(@Valid @RequestBody Seller seller){

        Seller addSeller = sellerService.addSeller(seller);

        System.out.println("Seller: "+seller);

        return new ResponseEntity<Seller>(addSeller, HttpStatus.CREATED);
    }



    //Get the list of seller-----------------------
    @GetMapping("/sellers")
    public ResponseEntity<List<Seller>> getAllSellerHandler(){

        List<Seller> sellers = sellerService.getAllSellers();

        return new ResponseEntity<List<Seller>>(sellers, HttpStatus.OK);
    }


    //Get the seller by Id............................
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<Seller> getSellerByIdHandler(@PathVariable("sellerId")Integer id){

        Seller getSeller = sellerService.getSellerById(id);

        return new ResponseEntity<Seller>(getSeller, HttpStatus.OK);
    }


    // Get Seller by mobile Number
    @GetMapping("/seller")
    public ResponseEntity<Seller> getSellerByMobileHandler(@RequestParam("mobile")String mobile, @RequestHeader("token")String token){
        Seller getSeller = sellerService.getSellerByMobile(mobile, token);

        return new ResponseEntity<Seller>(getSeller, HttpStatus.OK);
    }


    //Get currently logged in seller
    @GetMapping("/seller/current")
    public ResponseEntity<Seller> getLoggedInSellerHander(@RequestHeader("token")String token){

        Seller getSeller = sellerService.getCurrentlyLoggedInSeller(token);

        return new ResponseEntity<Seller>(getSeller, HttpStatus.OK);
    }



    //Update the seller..............................
    @PutMapping("/seller")
    public ResponseEntity<Seller> updateSellerHandler(@RequestBody Seller seller, @RequestHeader("token")String token){
        Seller updatedSeller = sellerService.updateSeller(seller, token);

        return new ResponseEntity<Seller>(updatedSeller, HttpStatus.ACCEPTED);
    }



    @PutMapping("/seller/update/mobile")
    public ResponseEntity<Seller> updateSellerMobileHandler(@Valid @RequestBody SellerDTO sellerDTO, @RequestHeader("token")String token){
        Seller updatedSeller = sellerService.updateSellerMobile(sellerDTO, token);

        return new ResponseEntity<Seller>(updatedSeller, HttpStatus.ACCEPTED);
    }



    @PutMapping("/seller/update/password")
    public ResponseEntity<SessionDTO> updateSellerPasswordHandler(@Valid @RequestBody SellerDTO sellerDTO, @RequestHeader("token")String token){
        return new ResponseEntity<>(sellerService.updateSellerPassword(sellerDTO, token), HttpStatus.ACCEPTED);
    }




    @PutMapping("/seller/{sellerId}")
    public ResponseEntity<Seller> deleteSellerByIdHandler(@PathVariable("sellerId") Integer id, @RequestHeader("token")String token){

        Seller deletedSeller = sellerService.deleteSellerById(id, token);

        return new ResponseEntity<Seller>(deletedSeller, HttpStatus.OK);
    }



}







































