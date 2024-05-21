package com.example.btl2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
                if (!((TextView) findViewById(R.id.menu_Email)).getText().toString().equals(user.getEmail())) {
                    ((TextView) findViewById(R.id.menu_Nickname)).setText(user.getUsername());
                    ((TextView) findViewById(R.id.menu_Email)).setText(user.getEmail());
                    ((ImageView) findViewById(R.id.avatarInMainActivity)).setImageBitmap(user.getAvatar());
                }
                drawerLayout.open();
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
                    Intent intent = new Intent(MainActivity.this, TermsAndConditionsActivity.class);
                    startActivity(intent);
                }

                if (itemId == R.id.itemAboutUs) {
                    //thuc hien hanh dong
                    String uriString = "https://github.com/minhdn0404/BTL2";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uriString));
                    startActivity(intent);
                }

                if (itemId == R.id.itemLogOut) {
                    //thuc hien hanh dong
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    finish();
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
                    replaceFragment(new MeFragment(user));
                }
                return true;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_ActivityMain, fragment);
        fragmentTransaction.commit();
    }
}