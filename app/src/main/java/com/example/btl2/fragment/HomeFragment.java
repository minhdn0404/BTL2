package com.example.btl2.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;

import com.example.btl2.CreateAuction;
import com.example.btl2.Details_Auction;
import com.example.btl2.MainActivity;
import com.example.btl2.models.HomeModel;
import com.example.btl2.ProductListAdapter;
import com.example.btl2.R;
import com.example.btl2.models.Product;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment  {

    RecyclerView homeRecycler;
    List<Product> productList;
    ProductListAdapter productListAdapter;
    ImageButton addProduct;
    public HomeFragment(List<Product> productList) {
        this.productList = productList;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeRecycler = root.findViewById(R.id.home_Recycler);
        addProduct = root.findViewById(R.id.addProduct);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        productListAdapter = new ProductListAdapter(getActivity(), productList);
        productListAdapter.setOnItemClickListener(product -> {
            Log.e("HomeFragment.java", "productListAdapter.setOnItemClickListener");
            Intent intent = new Intent(view.getContext(), Details_Auction.class);
            intent.putExtra("product", product.getId());
            startActivity(intent);
        });
        homeRecycler.setAdapter(productListAdapter);
        homeRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        homeRecycler.setHasFixedSize(true);
        homeRecycler.setNestedScrollingEnabled(false);

        addProduct.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), CreateAuction.class);
            startActivity(intent);
        });
        super.onViewCreated(view, savedInstanceState);
    }
}