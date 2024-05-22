package com.example.btl2;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btl2.api.FirebaseAPI;
import com.example.btl2.databinding.ActivityTermsAndConditionsBinding;
import com.example.btl2.models.User;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.concurrent.ExecutionException;

public class TermsAndConditionsActivity extends AppCompatActivity {
    private ActivityTermsAndConditionsBinding binding;
    private String termsAndConditions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTermsAndConditionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        update();

        binding.backButton.setOnClickListener(v -> {
            finish();
        });
    }

    private class GetTAC extends AsyncTask<Void, Void, String> {
        protected ProgressDialog dialog;
        protected Context context;

        public GetTAC(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Đang lấy các điều khoản và điều kiện");
            this.dialog.setCancelable(false);
            this.dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Task<String> task = FirebaseAPI.getTermsAndConditions();
                termsAndConditions = Tasks.await(task);
                return termsAndConditions;
            } catch (Exception e) {
                Log.v("ASYNC", "ERROR : " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Đăng ký OnFieldSelectedListener cho adapter
            termsAndConditions = result;
            binding.mainContent.setText(termsAndConditions);
            if (dialog.isShowing())
                dialog.dismiss();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void update() {
        GetTAC taskGetUser = new GetTAC(this);
        taskGetUser.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... strings) {
                try {
                    return taskGetUser.get();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                termsAndConditions = result.replace("\\n", "\n");
                binding.mainContent.setText(termsAndConditions);
            }
        }.execute();
    }
}