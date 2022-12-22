package com.example.eshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eshop.Cart;
import com.example.eshop.Product;
import com.example.eshop.R;
import com.example.eshop.databinding.CartItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context context;
    ArrayList<Cart> cartList;

    public CartAdapter(Context context, ArrayList<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);

        CartViewHolder ch = new CartAdapter.CartViewHolder(v);
        return ch;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        Cart cart = cartList.get(position);
        String price = Double.toString(cart.getPrice());
        holder.binding.cartName.setText(cart.getName());
        holder.binding.cartPrice.setText("Rs. " + price);
        holder.binding.remove.setTag(position);
        holder.binding.cartAmount.setText(cart.getQuantity() + " item(s)");
        Picasso.get().load(cart.getImg()).into(holder.binding.cartImg);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        CartItemBinding binding;
        public CartViewHolder(@NonNull View v) {
            super(v);
            binding = CartItemBinding.bind(v);
            binding.remove.setOnClickListener((View view)->{
                int pos = (int) view.getTag();
                Cart c = cartList.get(pos);
                cartList.remove(c);
                notifyDataSetChanged();
            });
        }
    }
}
