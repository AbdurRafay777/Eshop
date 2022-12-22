package com.example.eshop;

import com.example.eshop.DAO.ICartDAO;
import com.example.eshop.DAO.IProductDAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.UUID;

public class Cart implements Serializable {

    private String id;
    private String name, img;
    private double price;
    private int quantity;
    private transient ICartDAO dao = null;

    public Cart() {
        init();
    }

    public Cart(String name, String img, double price, int quantity) {
        init();
        this.name = name;
        this.img = img;
        this.price = price;
        this.quantity = quantity;
    }

    public Cart(String name, String img, double price, int quantity, ICartDAO dao) {
        init();
        this.name = name;
        this.img = img;
        this.price = price;
        this.quantity = quantity;
        this.dao = dao;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private void init(){
        this.id = UUID.randomUUID().toString();
    }

    public void save(){

        if (dao != null){

            Hashtable<String,String> data = new Hashtable<String, String>();

            data.put("id",id);
            data.put("name", name);
            data.put("img", img);
            data.put("price", price + "");
            data.put("quantity", quantity +"");

            dao.save(data);
        }
    }

    public void load(Hashtable<String,String> data){

        id = data.get("id");
        name = data.get("name");
        img = data.get("img");
        price = Double.parseDouble(data.get("price"));
        quantity = Integer.parseInt(data.get("quantity"));
    }

    public static ArrayList<Cart> load(ICartDAO dao){
        ArrayList<Cart> cartList = new ArrayList<Cart>();
        if(dao != null){

            ArrayList<Hashtable<String,String>> objects = dao.load();
            for(Hashtable<String,String> obj : objects){
                Cart cart = new Cart("","",0,0,dao);
                cart.load(obj);
                cartList.add(cart);
            }
        }
        return cartList;
    }
}
