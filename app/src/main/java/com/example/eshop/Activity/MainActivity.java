package com.example.eshop.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Filterable;

import com.example.eshop.Cart;
import com.example.eshop.DAO.CartDbDAO;
import com.example.eshop.DAO.ICartDAO;
import com.example.eshop.DAO.IProductDAO;
import com.example.eshop.DAO.ProductFirebaseDAO;
import com.example.eshop.DAO.ProductsDbDAO;
import com.example.eshop.Product;
import com.example.eshop.Adapter.ProductAdapter;
import com.example.eshop.ProductViewModel;
import com.example.eshop.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv;
    private RecyclerView.Adapter productAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<Product> products = new ArrayList<Product>();;
    private EditText search;
    Filterable filterable;
    IProductDAO productDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productDAO = new ProductFirebaseDAO(new ProductFirebaseDAO.DataObserver() {
            @Override
            public void update() {
                products = Product.load(productDAO);
                productAdapter = new ProductAdapter(MainActivity.this, products);
                rv.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));
                filterable = (Filterable) productAdapter;
                rv.setAdapter(productAdapter);
            }
        });

        search = findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterable.getFilter().filter(search.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        rv =  findViewById(R.id.productList);
        rv.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.support, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.support){
            String phone = "090078601";
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phone));
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}