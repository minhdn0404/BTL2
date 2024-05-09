package com.example.btl2.api;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.btl2.models.Product;
import com.example.btl2.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseAPI {
    public static FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private static final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    public static Task<User> signUp(Context context, String email, String password, String username, String phone) {
        TaskCompletionSource<User> taskCompletionSource = new TaskCompletionSource<>();
        fAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser user = fAuth.getCurrentUser();
                Toast.makeText(context, "Account Created!", Toast.LENGTH_SHORT).show();
                assert user != null;

                DatabaseReference userData = ref.child("Users").child(user.getUid());
                userData.child("username").setValue(username);
                userData.child("password").setValue(password);
                userData.child("email").setValue(email);
                userData.child("phone").setValue(phone);
                userData.child("isAdmin").setValue(false);

                User newUser = new User(username, phone, email, password, false);
                taskCompletionSource.setResult(newUser);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Failed to Create Account!", Toast.LENGTH_SHORT).show();
                taskCompletionSource.setResult(null);
            }
        });
        return taskCompletionSource.getTask();
    }

    public static Task<User> login(Context context, String email, String password) {
        TaskCompletionSource<User> taskCompletionSource = new TaskCompletionSource<>();
        fAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(context, "Login succeeded!", Toast.LENGTH_SHORT).show();

                // get user
                DatabaseReference userAPI = ref.child("Users").child(fAuth.getCurrentUser().getUid());;
                userAPI.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        taskCompletionSource.setResult(user);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Email or password wrong", Toast.LENGTH_SHORT).show();
                taskCompletionSource.setResult(null);
            }
        });
        return taskCompletionSource.getTask();
    }

    public static Task<ArrayList<Product>> getProductsByOwner(String uid) {
        TaskCompletionSource<ArrayList<Product>> taskCompletionSource = new TaskCompletionSource<>();
        DatabaseReference products = ref.child("Products");

        products.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Product> ans = new ArrayList<>();
                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    if (productSnapshot.child("owner").getValue(String.class) == uid) {
                        Product product = productSnapshot.getValue(Product.class);
                        product.setId(productSnapshot.getKey());
                        ans.add(product);
                    }
                }
                taskCompletionSource.setResult(ans);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                taskCompletionSource.setResult(null);
            }
        });
        return taskCompletionSource.getTask();
    }
}
