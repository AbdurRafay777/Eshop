package com.example.eshop.DAO;

import java.util.ArrayList;
import java.util.Hashtable;

public interface IProductDAO {

    public void save(Hashtable<String,String> attributes);
    public void save(ArrayList<Hashtable<String,String>> objects);
    public ArrayList<Hashtable<String,String>> load();
    public Hashtable<String,String> load(String id);
}
