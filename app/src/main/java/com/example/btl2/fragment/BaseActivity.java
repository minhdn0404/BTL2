package com.example.btl2.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.btl2.models.Product;
import com.example.btl2.models.User;

import java.util.List;
import java.util.Objects;

public class BaseActivity extends AppCompatActivity {
    public static User user;
    public static List<Product> productList;

    public static Product findProductByID(String id) {
        for (Product product : productList) {
            if (Objects.equals(product.getId(), id)) {
                return product;
            }
        }
        return null;
    }
}
