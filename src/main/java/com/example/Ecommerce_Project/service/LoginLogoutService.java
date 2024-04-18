package com.example.Ecommerce_Project.service;

import com.example.Ecommerce_Project.models.CustomerDTO;
import com.example.Ecommerce_Project.models.SellerDTO;
import com.example.Ecommerce_Project.models.SessionDTO;
import com.example.Ecommerce_Project.models.UserSession;

public interface LoginLogoutService {

    public UserSession loginCustomer(CustomerDTO customer);

    public SessionDTO logoutCustomer(SessionDTO sessionToken);

    public void checkTokenStatus(String token);

    public void deleteExpiredToken();

    public UserSession loginSeller(SellerDTO seller);

    public SessionDTO logoutSeller(SessionDTO session);

}
