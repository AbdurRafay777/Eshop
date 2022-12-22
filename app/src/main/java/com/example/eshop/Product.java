package com.example.eshop;

import com.example.eshop.DAO.IProductDAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.UUID;

public class Product implements Serializable {
    private String id;
    private String name, img, description;
    private double price;
    private transient IProductDAO dao = null;

    public Product(){
        init();
    }

    public Product(String name, String img, String description, double price) {
        init();
        this.name = name;
        this.img = img;
        this.description = description;
        this.price = price;
    }

    public Product(String name, String img, String description, double price, IProductDAO dao) {
        init();
        this.name = name;
        this.img = img;
        this.description = description;
        this.price = price;
        this.dao = dao;
    }

    public String getId(){ return id;}

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
            data.put("description", description);
            data.put("price", price + "");

            dao.save(data);
        }
    }

    public void load(Hashtable<String,String> data){

        id = data.get("id");
        name = data.get("name");
        img = data.get("img");
        description = data.get("description");
        price = Double.parseDouble(data.get("price"));
    }

    public static ArrayList<Product> load(IProductDAO dao){
        ArrayList<Product> products = new ArrayList<Product>();
        if(dao != null){

            ArrayList<Hashtable<String,String>> objects = dao.load();
            for(Hashtable<String,String> obj : objects){
                Product product = new Product("","","",0,dao);
                product.load(obj);
                products.add(product);
            }
        }
        return products;
    }
}
