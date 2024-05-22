package com.example.btl2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.btl2.adapters.DetailImageAdapter;
import com.example.btl2.adapters.ProductImageAdapter;
import com.example.btl2.api.FirebaseAPI;
import com.example.btl2.databinding.ActivityDetailsAuctionBinding;
import com.example.btl2.fragment.BaseActivity;
import com.example.btl2.models.Product;
import com.google.android.gms.tasks.Tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;

@SuppressLint("SetTextI18n")
public class Details_Auction extends BaseActivity {
    private Product product;
    private ActivityDetailsAuctionBinding binding;
    private DetailImageAdapter imageAdapter;

    DateTimeFormatter formatter;
    DateTimeFormatter formatter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsAuctionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String productID = getIntent().getStringExtra("product");

        Log.d("Details_Auction.java", productID);

        if (productID != "") {
            this.product = findProductByID(productID);
            Log.d("Details_Auction.java", this.product.toString());
            binding.productNameTextView.setText(product.getName());
            binding.productDescriptionTextView.setText("Mô tả sản phẩm: " + product.getDescription());
            binding.stepPriceTextView.setText("Bước giá nhỏ nhất: " + product.getStepPrice());

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                formatter = DateTimeFormatter.ofPattern("HH:mm  dd-MM-yyyy");
                formatter1 = DateTimeFormatter.ofPattern("HH:mm:ss");
                LocalDateTime startTime = LocalDateTime.parse(product.getAuctionStartTime(), formatter);
                LocalDateTime endTime = LocalDateTime.parse(product.getAuctionEndTime(), formatter);

                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        updateTime(startTime, endTime);
                        updateCurrentPrice();
                        handler.postDelayed(this, 500);
                    }
                };
                runnable.run();
            }
        }
        imageAdapter = new DetailImageAdapter(this, product.getImage());
        binding.productDetailImages.setAdapter(imageAdapter);
    }

    private void updateCurrentPrice() {
        new AsyncTask<Product, Void, Integer>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            protected Integer doInBackground(Product... products) {
                try {
                    product.setCurrentPrice(Tasks.await(FirebaseAPI.getCurrentPrice(product)));
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return product.getCurrentPrice();
            }
        }.execute();

        if (product.getCurrentPrice() == 0) {
            binding.currentPriceTextView.setText("Giá hiện tại: " + product.getStartPrice());
        } else {
            binding.currentPriceTextView.setText("Giá hiện tại: " + product.getCurrentPrice());
        }
    }

    private void updateTime(LocalDateTime startTime, LocalDateTime endTime) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (startTime.isBefore(LocalDateTime.now())) {
                String duration = getDuration(LocalDateTime.now(), endTime);
                binding.timeRemainingTextView.setText("Thời gian đến khi kết thúc: " + duration);
            } else {
                String duration = getDuration(LocalDateTime.now(), startTime);
                binding.timeRemainingTextView.setText("Thời gian đến khi bắt đầu: " + duration);
            }
        }
    }

    private String getDuration(LocalDateTime time1, LocalDateTime time2) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Duration duration = Duration.between(time1, time2);

            // Quy đổi Duration ra LocalTime
            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60;
            long seconds = duration.getSeconds() % 60;

            LocalTime timeDifference = LocalTime.of((int) hours, (int) minutes, (int) seconds);

            String timeString = timeDifference.format(formatter1);
            return timeString;
        } else {
            return null;
        }
    }
}