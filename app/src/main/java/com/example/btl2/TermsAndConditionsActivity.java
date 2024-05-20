package com.example.btl2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btl2.fragment.HomeFragment;

public class TermsAndConditionsActivity extends AppCompatActivity {
    ScrollView scrollView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        button = (Button) findViewById(R.id.btBack);
    }
}