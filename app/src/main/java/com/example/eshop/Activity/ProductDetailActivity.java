package com.example.eshop.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eshop.Activity.CartActivity;
import com.example.eshop.Cart;
import com.example.eshop.DAO.CartDbDAO;
import com.example.eshop.DAO.CartFirebaseDAO;
import com.example.eshop.DAO.ICartDAO;
import com.example.eshop.DAO.IProductDAO;
import com.example.eshop.DAO.ProductsDbDAO;
import com.example.eshop.Product;
import com.example.eshop.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class ProductDetailActivity extends AppCompatActivity implements Serializable {

    Product product;
    TextView product_name;
    TextView product_price;
    TextView product_description;
    ImageView product_img;
    Button addToCart;
    ICartDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);
        dao = new CartFirebaseDAO(new CartFirebaseDAO.DataObserver() {
            @Override
            public void update() {
                return;
            }
        });

        product_name = (TextView) findViewById(R.id.product_name);
        product_price = (TextView) findViewById(R.id.product_price);
        product_description = (TextView) findViewById(R.id.product_description);
        product_img = (ImageView) findViewById(R.id.product_img);
        addToCart = (Button) findViewById(R.id.addToCart);

        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        String img = intent.getStringExtra("img");
        Double price = intent.getDoubleExtra("price", 0);

        product = new Product(name,img,description,price);

        product_name.setText(name);
        product_price.setText("Rs. " + price);
        product_description.setText(description);
        Picasso.get().load(img).into(product_img);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addToCart.setOnClickListener((View v) ->{
            Cart cart = new Cart(name,img,price,1,dao);
            cart.save();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.cart){
            startActivity(new Intent(this, CartActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}