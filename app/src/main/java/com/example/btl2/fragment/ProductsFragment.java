package com.example.btl2.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.btl2.ProductListAdapter;
import com.example.btl2.R;
import com.example.btl2.databinding.FragmentProductsBinding;
import com.example.btl2.models.Product;
import com.example.btl2.models.User;

import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment {
    RecyclerView productRecycler;
    ProductListAdapter productListAdapter2;
    private FragmentProductsBinding binding;
    List<Product> productList;
    User user;
    public ProductsFragment(List<Product> productList, User user) {
        this.productList = productList;
        this.user = user;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductsBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        productRecycler = binding.productRecycler;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Product> filteredProductlist = new ArrayList<>();
        for (Product product : productList) {
            if (product.getOwner().equals(user.getId())) {
                filteredProductlist.add(product);
            }
        }

        productListAdapter2 = new ProductListAdapter(getActivity(), filteredProductlist);
        productRecycler.setAdapter(productListAdapter2);
        productRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        productRecycler.setHasFixedSize(true);
        productRecycler.setNestedScrollingEnabled(false);
        // TODO: Hiện ra những sản phẩm do mình tạo ra
    }
}