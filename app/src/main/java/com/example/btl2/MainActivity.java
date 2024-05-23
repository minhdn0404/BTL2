package com.example.btl2;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import com.example.btl2.api.FirebaseAPI;
import com.example.btl2.fragment.BaseActivity;
import com.example.btl2.fragment.BidsFragment;
import com.example.btl2.fragment.HomeFragment;
import com.example.btl2.fragment.MeFragment;
import com.example.btl2.fragment.NotiFragment;
import com.example.btl2.fragment.ProductsFragment;
import com.example.btl2.models.Product;
import com.example.btl2.models.User;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends BaseActivity {

    DrawerLayout drawerLayout;
    ImageButton buttonDrawerToggle;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    List<Product> productList = new ArrayList<>();

    List<Product> searchProductList = new ArrayList<>();

    @Override
    protected void onStart() {
        getAllProducts();
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        buttonDrawerToggle = findViewById(R.id.buttonDrawerToggle);
        navigationView = findViewById(R.id.navigationView);
        bottomNavigationView = findViewById(R.id.bottomNavigation);


        buttonDrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });

        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (!((TextView) findViewById(R.id.menu_Email)).getText().toString().equals(user.getEmail())) {
                    ((TextView) findViewById(R.id.menu_Nickname)).setText(user.getUsername());
                    ((TextView) findViewById(R.id.menu_Email)).setText(user.getEmail());
                    ((ImageView) findViewById(R.id.avatarInMainActivity)).setImageBitmap(user.getAvatar());
                }
                super.onDrawerSlide(drawerView, slideOffset);
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
                    replaceFragment(new HomeFragment(productList));
                }
                if (menuItem.getItemId() == R.id.item_Bids) {
                    replaceFragment(new BidsFragment());
                }
                if (menuItem.getItemId() == R.id.item_Noti) {
                    replaceFragment(new NotiFragment());
                }
                if (menuItem.getItemId() == R.id.item_Products) {
                    replaceFragment(new ProductsFragment(productList, user));
                }
                if (menuItem.getItemId() == R.id.item_Me) {
                    replaceFragment(new MeFragment(user));
                }
//                if(menuItem.getItemId()== R.id.searchProduct){
////                    replaceFragment(new HomeFragment(searchProductList));
//                    EditText editTextSearchProduct;
//                    editTextSearchProduct = findViewById(R.id.search_Bar);
//                    String searchProductText = editTextSearchProduct.getText().toString().trim();
//                    replaceFragment(new HomeFragment(searchProductText));
//                }
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

    private class GetAllProducts extends AsyncTask<Void, Void, List<Product>> {
        protected ProgressDialog dialog;
        protected Context context;

        public GetAllProducts(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Đang lấy dữ liệu");
            this.dialog.setCancelable(false);
            this.dialog.show();
        }

        @Override
        protected List<Product> doInBackground(Void... params) {
            try {
                Task<List<Product>> task = FirebaseAPI.getAllProducts();
                productList = Tasks.await(task);
                for (int i = 0; i < productList.size(); i++) {
                    Task<Product> task1 = FirebaseAPI.getImageOfProduct(productList.get(i));
                    productList.set(i, Tasks.await(task1));
                }
                Log.d("MainActivity.java", "class GetAllProducts, doInBackground() \n Task1: " + productList.get(0).toString());
                return productList;
            } catch (Exception e) {
                Log.v("ASYNC", "ERROR : " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Product> result) {
            super.onPostExecute(result);
            // Đăng ký OnFieldSelectedListener cho adapter
            productList = result;
            replaceFragment(new HomeFragment(productList));
            if (dialog.isShowing())
                dialog.dismiss();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void getAllProducts() {
        GetAllProducts taskGetUser = new GetAllProducts(this);
        taskGetUser.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new AsyncTask<Void, Void, List<Product>>() {
            @Override
            protected List<Product> doInBackground(Void... strings) {
                try {
                    return taskGetUser.get();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
}