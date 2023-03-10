package com.example.eshop.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class CartDbDAO implements ICartDAO{

    private Context context;

    public CartDbDAO(Context ctx){
        context = ctx;
    }

    @Override
    public void save(Hashtable<String, String> attributes) {
        ProductsDbHelper dbHelper = new ProductsDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues content = new ContentValues();
        Enumeration<String> keys = attributes.keys();
        while (keys.hasMoreElements()){
            String key = keys.nextElement();
            content.put(key,attributes.get(key));
        }

        String [] arguments = new String[1];
        arguments[0] = attributes.get("id");
        Hashtable obj = load(arguments[0]);

        if (obj.get("id") != null && obj.get("id").equals(arguments[0])){
            db.update("Cart",content,"id = ?",arguments);
        }
        else{
            db.insert("Cart",null,content);
        }
    }

    @Override
    public void save(ArrayList<Hashtable<String, String>> objects) {
        for(Hashtable<String,String> obj : objects){
            save(obj);
        }
    }

    @SuppressLint("Range")
    @Override
    public ArrayList<Hashtable<String, String>> load() {
        ProductsDbHelper dbHelper = new ProductsDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM Cart";
        Cursor cursor = db.rawQuery(query,null);

        ArrayList<Hashtable<String,String>> objects = new ArrayList<Hashtable<String, String>>();
        while(cursor.moveToNext()){
            Hashtable<String,String> obj = new Hashtable<String, String>();
            String [] columns = cursor.getColumnNames();
            for(String col : columns){
                obj.put(col.toLowerCase(),cursor.getString(cursor.getColumnIndex(col)));
            }
            objects.add(obj);
        }

        return objects;
    }

    @SuppressLint("Range")
    @Override
    public Hashtable<String, String> load(String id) {
        ProductsDbHelper dbHelper = new ProductsDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM Cart WHERE id = ?";
        String[] arguments = new String[1];
        arguments[0] = id;
        Cursor cursor = db.rawQuery(query,arguments);


        Hashtable<String,String> obj = new Hashtable<String, String>();
        while(cursor.moveToNext()){
            String [] columns = cursor.getColumnNames();
            for(String col : columns){
                obj.put(col.toLowerCase(),cursor.getString(cursor.getColumnIndex(col)));
            }
        }

        return obj;
    }
}
