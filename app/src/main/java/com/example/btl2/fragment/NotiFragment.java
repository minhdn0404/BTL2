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

import com.example.btl2.NotiModelAdapter;
import com.example.btl2.R;
import com.example.btl2.models.NotiModel;

import java.util.ArrayList;
import java.util.List;

public class NotiFragment extends Fragment {
    RecyclerView notiRecycler;
    List<NotiModel> notiModelList;
    NotiModelAdapter notiModelAdapter;

    int[] image = {R.drawable.image1, R.drawable.image2};
    String[] headNoti = {"Bạn có đơn hàng đang trên đường giao", "Bạn có đơn hàng đang trên đường giao"};
    String[] noti = {"Đơn hàng của bạn sẽ được giao trong 1-2 ngày tới.", "Đơn hàng của bạn sẽ được giao trong 1-2 ngày tới."};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_noti, container, false);
        notiRecycler = root.findViewById(R.id.noti_Recycler);
        notiModelList = new ArrayList<>();
        for(int i = 0; i < noti.length; i++)
        {
            notiModelList.add(new NotiModel(image[i], headNoti[i], noti[i]));
        }
        notiModelAdapter = new NotiModelAdapter(getActivity(), notiModelList);
        notiRecycler.setAdapter(notiModelAdapter);
        notiRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        notiRecycler.setHasFixedSize(true);
        notiRecycler.setNestedScrollingEnabled(false);
        return root;
    }
}