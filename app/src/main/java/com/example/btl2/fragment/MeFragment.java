package com.example.btl2.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.btl2.R;
import com.example.btl2.databinding.FragmentMeBinding;
import com.example.btl2.models.User;

public class MeFragment extends Fragment {
    private FragmentMeBinding binding;
    private User user;

    public MeFragment(User user) {
        this.user = user;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMeBinding.inflate(inflater, container, false);
        binding.imageView.setImageBitmap(user.getAvatar());
        binding.textView.setText(user.getUsername());
        binding.textView2.setText(user.getEmail());
        binding.textView11.setText(user.getUsername());
        binding.textView12.setText(user.getEmail());
        binding.textView4.setText(user.getPhone());
        return binding.getRoot();
    }
}