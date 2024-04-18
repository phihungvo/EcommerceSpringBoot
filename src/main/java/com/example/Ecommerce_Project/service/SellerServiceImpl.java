package com.example.Ecommerce_Project.service;

import com.example.Ecommerce_Project.exception.LoginException;
import com.example.Ecommerce_Project.exception.SellerException;
import com.example.Ecommerce_Project.models.Seller;
import com.example.Ecommerce_Project.models.SellerDTO;
import com.example.Ecommerce_Project.models.SessionDTO;
import com.example.Ecommerce_Project.models.UserSession;
import com.example.Ecommerce_Project.repository.SellerDao;
import com.example.Ecommerce_Project.repository.SessionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService{

    @Autowired
    private SellerDao sellerDao;

    @Autowired
    private LoginLogoutService loginService;

    @Autowired
    private SessionDao sessionDao;


    @Override
    public Seller addSeller(Seller seller) {
        return sellerDao.save(seller);
    }

    @Override
    public List<Seller> getAllSellers() throws SellerException {

        List<Seller> sellers = sellerDao.findAll();

        if(sellers.size() > 0 ){
            return sellers;
        }
        else throw new SellerException("No seller found!");
    }

    @Override
    public Seller getSellerById(Integer sellerId) throws SellerException {

        Optional<Seller> seller = sellerDao.findById(sellerId);

        if(seller.isPresent()){
            return seller.get();
        }
        else throw new SellerException("Seller not found for this ID: "+sellerId);
    }

    @Override
    public Seller getSellerByMobile(String mobile, String token) throws SellerException {
        if(token.contains("seller") == false){
            throw new LoginException("Invalid session token for seller");
        }

        loginService.checkTokenStatus(token);

        Seller existingSeller = sellerDao.findByMobile(mobile).orElseThrow( () ->
                                new SellerException("Seller not found with given mobile"));
        return existingSeller;
    }

    @Override
    public Seller getCurrentlyLoggedInSeller(String token) throws SellerException {

        if(token.contains("seller") == false){
            throw new LoginException("Invalid session token for seller");
        }

        loginService.checkTokenStatus(token);

        UserSession user = sessionDao.findByToken(token).get();

        Seller existingSeller = sellerDao.findById(user.getUserId()).orElseThrow( () ->
                                        new SellerException("Seller not found for this ID"));

        return existingSeller;
    }



    //Method to update password - base on current token
    @Override
    public SessionDTO updateSellerPassword(SellerDTO sellerDTO, String token) throws SellerException {

        if(token.contains("seller") == false){
            throw new LoginException("Invalid session token for seller");
        }

        loginService.checkTokenStatus(token);

        UserSession user = sessionDao.findByToken(token).get();

        Optional<Seller> opt = sellerDao.findById(user.getUserId());

        if(opt.isEmpty())
            throw new SellerException("Seller does not exist");

        Seller existingSeller = opt.get();

        if(sellerDTO.getMobile().equals(existingSeller.getMobile()) == false){
            throw new SellerException("Verification error. Mobile number does not match");
        }

        existingSeller.setPassword(sellerDTO.getPassword());

        sellerDao.save(existingSeller);

        SessionDTO session = new SessionDTO();

        session.setToken(token);

        loginService.logoutSeller(session);

        session.setMessage("Updated password and logged out. Login again with new password");

        return session;
    }

    @Override
    public Seller updateSeller(Seller seller, String token) throws SellerException {

        if(token.contains("seller") == false)
            throw new LoginException("Invalid session token for seller");

        loginService.checkTokenStatus(token);

        Seller existingSeller = sellerDao.findById(seller.getSellerId()).orElseThrow( () ->
                          new SellerException("Seller not found for this ID: "+ seller.getSellerId()));
        Seller newSeller = sellerDao.save(seller);
        return newSeller;
    }

    @Override
    public Seller updateSellerMobile(SellerDTO sellerDTO, String token) throws SellerException {

        if(token.contains("seller") == false){
            throw new LoginException("Invalid session token for seller");
        }

        loginService.checkTokenStatus(token);

        UserSession user = sessionDao.findByToken(token).get();

        Seller existingSeller = sellerDao.findById(user.getUserId()).orElseThrow( () ->
                new SellerException("Seller not found for this ID: "+user.getUserId()));

        if(existingSeller.getPassword().equals(sellerDTO.getPassword())){
            existingSeller.setMobile(sellerDTO.getMobile());
            return sellerDao.save(existingSeller);
        }else {
            throw new SellerException("Error occured in updating mobile. Please enter correct password");
        }
    }

    @Override
    public Seller deleteSellerById(Integer sellerId, String token) throws SellerException {

        if(token.contains("seller") == false){
            throw new LoginException("Invalid session token for seller");
        }

        loginService.checkTokenStatus(token);

        Optional<Seller> opt = sellerDao.findById(sellerId);

        if(opt.isPresent()){

            UserSession user = sessionDao.findByToken(token).get();

            Seller existingSeller = opt.get();

            if(user.getUserId() == existingSeller.getSellerId()){
                sellerDao.delete(existingSeller);

                //logic to logout a seller after he deletes his account
                SessionDTO session = new SessionDTO();
                session.setToken(token);
                loginService.logoutSeller(session);

                return existingSeller;
            }
            else{
                throw new SellerException("Verification error in deleting seller account");
            }
        }
        else throw new SellerException(("Seller not found for this ID: ")+sellerId);
    }
}






























