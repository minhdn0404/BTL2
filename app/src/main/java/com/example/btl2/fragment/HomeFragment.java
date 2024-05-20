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
import android.widget.ImageButton;

import com.example.btl2.CreateAuction;
import com.example.btl2.MainActivity;
import com.example.btl2.models.HomeModel;
import com.example.btl2.HomeModelAdapter;
import com.example.btl2.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView homeRecycler;
    List<HomeModel> homeModelList;
    HomeModelAdapter homeModelAdapter;
    ImageButton addProduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeRecycler = root.findViewById(R.id.home_Recycler);
        addProduct = root.findViewById(R.id.addProduct);

        homeModelList = new ArrayList<>();
        homeModelList.add(new HomeModel(R.drawable.image1,100,20,"M42-EOS","11:00 - 21:00 08/05/2024"));
        homeModelList.add(new HomeModel(R.drawable.image2,150,40,"Canon 750D","12:00 - 20:00 09/05/2024"));
        homeModelList.add(new HomeModel(R.drawable.image3,170,20,"Canon 1DX","13:00 - 23:00 11/05/2024"));
        homeModelList.add(new HomeModel(R.drawable.image4,130,10,"Canon 5D2","14:00 - 22:00 12/05/2024"));
        homeModelList.add(new HomeModel(R.drawable.image5,120,20,"Canon 600D","15:00 - 22:00 12/05/2024"));
        homeModelList.add(new HomeModel(R.drawable.image1,190,70,"Nikon D300","12:00 - 22:00 16/05/2024"));
        homeModelList.add(new HomeModel(R.drawable.image2,140,30,"Sony A73","11:00 - 22:00 18/05/2024"));
        // Inflate the layout for this fragment

        homeModelAdapter = new HomeModelAdapter(getActivity(), homeModelList);
        homeRecycler.setAdapter(homeModelAdapter);
        homeRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        homeRecycler.setHasFixedSize(true);
        homeRecycler.setNestedScrollingEnabled(false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        addProduct.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), CreateAuction.class);
            startActivity(intent);
        });
        super.onViewCreated(view, savedInstanceState);
    }
}