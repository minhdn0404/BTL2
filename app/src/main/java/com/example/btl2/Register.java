package com.example.btl2;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btl2.api.FirebaseAPI;
import com.example.btl2.fragment.BaseActivity;
import com.example.btl2.models.User;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Base64;
import java.util.concurrent.ExecutionException;

public class Register extends BaseActivity {
    EditText editTextName, editTextPhone, editTextEmail, editTextPassword;
    Button btnRegister;
    TextView textViewLogin;

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.editText_Name);
        editTextEmail = findViewById(R.id.editText_Email);
        editTextPassword = findViewById(R.id.editText_Password);
        editTextPhone = findViewById(R.id.editText_Phone);
        btnRegister = findViewById(R.id.button_Register);
        textViewLogin = findViewById(R.id.text_Login);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password, username, phone;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());
                username = String.valueOf(editTextName.getText());
                phone = String.valueOf(editTextPhone.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Register.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Register.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(Register.this, "Enter username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(Register.this, "Enter phone", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(Register.this, "Password must be >= 6", Toast.LENGTH_SHORT).show();
                }

                signUp(email, password, username, phone);
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    private class GetUser extends AsyncTask<String, Void, User> {
        protected ProgressDialog dialog;
        protected Context context;

        public GetUser(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Đang đăng ký");
            this.dialog.show();
        }

        @Override
        protected User doInBackground(String... params) {
            try {
                Task<User> task = FirebaseAPI.signUp(Register.this, (String) params[0], (String) params[1], (String) params[2], (String) params[3]);
                user = Tasks.await(task);
                return user;
            } catch (Exception e) {
                Log.v("ASYNC", "ERROR : " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(User result) {
            super.onPostExecute(result);
            // Đăng ký OnFieldSelectedListener cho adapter
            user = result;
            if (dialog.isShowing())
                dialog.dismiss();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void signUp(String email, String password, String username, String phone) {
        GetUser taskGetUser = new GetUser(this);
        taskGetUser.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, email, password, username, phone);
        new AsyncTask<String, Void, User>() {
            @Override
            protected User doInBackground(String... strings) {
                try {
                    return taskGetUser.get();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }


            @Override
            protected void onPostExecute(User result) {
                user = result;
                super.onPostExecute(result);
            }
        }.execute();
    }
}