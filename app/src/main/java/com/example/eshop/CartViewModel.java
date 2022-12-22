package com.example.eshop;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import com.example.eshop.DAO.ICartDAO;

import java.util.ArrayList;

public class CartViewModel extends ViewModel {
    private ArrayList<Cart> cart;
    ICartDAO dao;

    public ArrayList<Cart> getNotes(Bundle savedInstanceState, String key){
        if (cart == null){
            if (savedInstanceState == null) {
                if (dao != null){
                    cart = Cart.load(dao);
                }
                else cart = new ArrayList<Cart>();
            }
            else{
                cart = (ArrayList<Cart>) savedInstanceState.get(key);
            }
        }
        return cart;
    }

    public void setDao(ICartDAO d){
        dao = d;
    }
    public ArrayList<Cart> update(){
        if (dao != null){
            cart = Cart.load(dao);
        }
        else cart = new ArrayList<Cart>();
        return cart;
    }
}
