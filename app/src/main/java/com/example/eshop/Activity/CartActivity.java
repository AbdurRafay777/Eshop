package com.example.eshop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.eshop.Adapter.CartAdapter;
import com.example.eshop.Cart;
import com.example.eshop.DAO.CartDbDAO;
import com.example.eshop.DAO.CartFirebaseDAO;
import com.example.eshop.DAO.ICartDAO;
import com.example.eshop.DAO.IProductDAO;
import com.example.eshop.DAO.ProductsDbDAO;
import com.example.eshop.Product;
import com.example.eshop.R;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private RecyclerView rv;
    private RecyclerView.Adapter cartAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<Cart> cartItems = new ArrayList<Cart>();
    TextView cartTotal;
    ICartDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        dao = new CartFirebaseDAO(new CartFirebaseDAO.DataObserver() {
            @Override
            public void update() {
                cartItems = Cart.load(dao);
                cartAdapter = new CartAdapter(CartActivity.this, cartItems);
                rv.addItemDecoration(new DividerItemDecoration(CartActivity.this, LinearLayoutManager.VERTICAL));
                rv.setAdapter(cartAdapter);

                cartTotal = (TextView) findViewById(R.id.total);
                cartTotal.setText(getCartTotal(cartItems) + "");
            }
        });



        rv =  findViewById(R.id.cartList);
        rv.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public double getCartTotal(ArrayList<Cart> cartItems){
        double total = 0;
        for(Cart p:cartItems){
            total += (p.getPrice() * p.getQuantity());
        }
        return total;
    }
}
