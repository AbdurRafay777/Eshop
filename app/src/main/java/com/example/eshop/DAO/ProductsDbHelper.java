package com.example.eshop.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProductsDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Products.db";
    public ProductsDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String productSQL = "CREATE TABLE Products (Id TEXT PRIMARY KEY, " +
                "Name TEXT," +
                "Img TEXT," +
                "Description TEXT," +
                "Price TEXT)";

        String cartSQL = "CREATE TABLE Cart (Id TEXT PRIMARY KEY, " +
                "Name TEXT," +
                "Img TEXT," +
                "Price TEXT," +
                "Quantity TEXT)";
        db.execSQL(productSQL);
        db.execSQL(cartSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Products");
        db.execSQL("DROP TABLE IF EXISTS Cart");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
