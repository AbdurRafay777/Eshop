package com.example.eshop.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class ProductFirebaseDAO implements  IProductDAO {

    public interface DataObserver{
        public void update();
    }

    private DataObserver observer;
    FirebaseDatabase database;
    DatabaseReference reference;

    ArrayList<Hashtable<String,String>> products;

    public ProductFirebaseDAO(DataObserver observer) {
        this.observer = observer;
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("products");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    products = new ArrayList<Hashtable<String,String>>();
                    for (DataSnapshot d : snapshot.getChildren()) {
                        GenericTypeIndicator<HashMap<String,Object>> type = new GenericTypeIndicator<HashMap<String, Object>>() {};
                        HashMap<String,Object> map =  d.getValue(type);

                        Hashtable<String,String> obj = new Hashtable<String,String>();
                        for(String key : map.keySet()){
                            obj.put(key,map.get(key).toString());
                        }
                        products.add(obj);
                    }

                    observer.update();
                }
                catch (Exception ex) {
                    Log.e("firebasedb", ex.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("firebasedb", "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public void save(Hashtable<String, String> attributes) {
        reference.child(attributes.get("id")).setValue(attributes);
    }

    @Override
    public void save(ArrayList<Hashtable<String, String>> objects) {
        for(Hashtable<String,String> obj : objects){
            save(obj);
        }
    }

    @Override
    public ArrayList<Hashtable<String, String>> load() {
        if (products == null){
            products = new ArrayList<Hashtable<String,String>>();
        }
        return products;
    }

    @Override
    public Hashtable<String, String> load(String id) {
        return null;
    }
}
