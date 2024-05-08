package com.example.btl2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.example.btl2.fragment.BaseActivity;
import com.example.btl2.fragment.BidsFragment;
import com.example.btl2.fragment.HomeFragment;
import com.example.btl2.fragment.MeFragment;
import com.example.btl2.fragment.NotiFragment;
import com.example.btl2.fragment.ProductsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends BaseActivity {

    DrawerLayout drawerLayout;
    ImageButton buttonDrawerToggle;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragment(new HomeFragment());

        drawerLayout = findViewById(R.id.drawerLayout);
        buttonDrawerToggle = findViewById(R.id.buttonDrawerToggle);
        navigationView = findViewById(R.id.navigationView);
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        buttonDrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
                ((TextView) findViewById(R.id.menu_Nickname)).setText(user.getUsername());
                ((TextView) findViewById(R.id.menu_Email)).setText(user.getEmail());
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int itemId = menuItem.getItemId();

                if (itemId == R.id.itemSetting) {
                    //thuc hien hanh dong
                }

                if (itemId == R.id.itemHistory) {
                    //thuc hien hanh dong
                }

                if (itemId == R.id.itemTermsAndConditions) {
                    //thuc hien hanh dong
                }

                if (itemId == R.id.itemAboutUs) {
                    //thuc hien hanh dong
                }

                if (itemId == R.id.itemShare) {
                    //thuc hien hanh dong
                }

                if (itemId == R.id.itemLogOut) {
                    //thuc hien hanh dong
                }

                drawerLayout.close();

                return false;
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.item_Home) {
                    replaceFragment(new HomeFragment());
                }
                if (menuItem.getItemId() == R.id.item_Bids) {
                    replaceFragment(new BidsFragment());
                }
                if (menuItem.getItemId() == R.id.item_Noti) {
                    replaceFragment(new NotiFragment());
                }
                if (menuItem.getItemId() == R.id.item_Products) {
                    replaceFragment(new ProductsFragment());
                }
                if (menuItem.getItemId() == R.id.item_Me) {
                    replaceFragment(new MeFragment());
                }
                return true;
            }
        });

//        auth = FirebaseAuth.getInstance();
//        btnLogout = findViewById(R.id.logOut);
//        textView = findViewById(R.id.userDetails);
//        user = auth.getCurrentUser();
//        if (user == null) {
//            Intent intent = new Intent(getApplicationContext(), Login.class);
//            startActivity(intent);
//            finish();
//        } else {
//            textView.setText(user.getDisplayName());
//        }
//
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intent = new Intent(getApplicationContext(), Login.class);
//                startActivity(intent);
//                finish();
//            }
//        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_ActivityMain, fragment);
        fragmentTransaction.commit();
    }
}