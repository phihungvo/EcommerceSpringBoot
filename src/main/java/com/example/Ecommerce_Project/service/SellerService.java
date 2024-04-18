package com.example.Ecommerce_Project.service;

import com.example.Ecommerce_Project.exception.SellerException;
import com.example.Ecommerce_Project.models.Seller;
import com.example.Ecommerce_Project.models.SellerDTO;
import com.example.Ecommerce_Project.models.SessionDTO;

import java.util.List;

public interface SellerService {

    public Seller addSeller(Seller seller);

    public List<Seller> getAllSellers() throws SellerException;

    public Seller getSellerById(Integer sellerId) throws SellerException;

    public Seller getSellerByMobile(String mobile, String token) throws SellerException;

    public Seller getCurrentlyLoggedInSeller(String token) throws SellerException;

    public SessionDTO updateSellerPassword(SellerDTO sellerDTO, String token) throws SellerException;

    public Seller updateSeller(Seller seller, String token) throws SellerException;

    public Seller updateSellerMobile(SellerDTO sellerDTO, String token) throws SellerException;

    public Seller deleteSellerById(Integer sellerId, String token) throws SellerException;






}





























