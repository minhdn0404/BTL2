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

import java.util.concurrent.ExecutionException;

public class Login extends BaseActivity {
    EditText editTextEmail, editTextPassword;
    Button btnLogin;
    TextView textViewRegister;
    FirebaseAuth mAuth;

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
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editText_Email);
        editTextPassword = findViewById(R.id.editText_Password);
        btnLogin = findViewById(R.id.button_login);
        textViewRegister = findViewById(R.id.text_Register);
        mAuth = FirebaseAuth.getInstance();

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    editTextEmail.setError("Enter username");
                    editTextEmail.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    editTextPassword.setError("Enter password");
                    editTextPassword.requestFocus();
                    return;
                }

                login(email, password);
                if (user != null) {
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                }
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
            this.dialog.setMessage("Đang đăng nhập");
            this.dialog.show();
        }

        @Override
        protected User doInBackground(String... params) {
            try {
                Task<User> task = FirebaseAPI.login(Login.this, (String) params[0], (String) params[1]);
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
    private void login(String email, String password) {
        GetUser taskGetUser = new GetUser(this);
        taskGetUser.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, email, password);
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
            }
        }.execute();
    }
}