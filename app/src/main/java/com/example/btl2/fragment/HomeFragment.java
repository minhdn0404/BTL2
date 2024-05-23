package com.example.btl2.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.btl2.CreateAuction;
import com.example.btl2.ProductListAdapter;
import com.example.btl2.R;
import com.example.btl2.models.Product;

import java.util.ArrayList;
import java.util.List;

//import androidx.appcompat.app.AppCompatActivity;
public class HomeFragment extends Fragment {

    RecyclerView homeRecycler;
    List<Product> productList;

    List<Product> productListView = new ArrayList<>();

//    List<Product> searchProductList;
    ProductListAdapter productListAdapter;
    ImageButton addProduct;

    ImageButton searchProduct;

    EditText editTextSearchProduct;

    Boolean showSearchProduct = false;
    public HomeFragment(List<Product> productList) {
        this.productList = productList;
    }

//    public HomeFragment(String searchText){
//        if(!searchText.isEmpty() &&  searchText!=" "){
//            List<Product> result = new ArrayList<>();
//            for(int i=0; i<productList.size(); i++){
//                if(productList.get(i).getName().contains(searchText)){
//                    result.add(productList.get(i));
//                }
//            }
//            this.productList = result;
//        }
//        this.productList = productList;
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeRecycler = root.findViewById(R.id.home_Recycler);
        addProduct = root.findViewById(R.id.addProduct);
        searchProduct = root.findViewById(R.id.searchProduct);
        editTextSearchProduct = root.findViewById(R.id.search_Bar);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        String searchProductText = editTextSearchProduct.getText().toString();

        searchProduct.setOnClickListener(v -> {
//            System.out.println("bam nut roi ne");
//            System.out.println(searchProductText);
            this.setProductListView(searchProductText, true);

        });
        if(this.showSearchProduct == false){
            productListAdapter = new ProductListAdapter(getActivity(), productList);
        }
        else{
            productListAdapter = new ProductListAdapter(getActivity(), productListView);
        }
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

    public void setProductListView(String searchProductText, Boolean showSearchProduct){
        if(showSearchProduct == true){
            this.showSearchProduct = true;
            if(this.productListView != null){
                this.productListView = null;
                this.productListView = new ArrayList<>();
            }

            for(int i=0; i<this.productList.size(); i++){
                if(this.productList.get(i).getName().contains(searchProductText)){
                    this.productListView.add(this.productList.get(i));
                }
            }
        }
        else{
            this.showSearchProduct = false;
            this.productListView = null;
            this.productListView = new ArrayList<>();
        }
    }

}