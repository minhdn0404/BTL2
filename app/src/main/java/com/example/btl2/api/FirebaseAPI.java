package com.example.btl2.api;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.BoringLayout;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FirebaseAPI {
    public static FirebaseAuth fAuth = FirebaseAuth.getInstance();
    static protected FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    public static void signUp(Context context, String email, String password) {
        fAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser user = fAuth.getCurrentUser();
                Toast.makeText(context, "Account Created!", Toast.LENGTH_SHORT).show();
                assert user != null;
                DocumentReference df = fStore.collection("Users").document(user.getUid());
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("UserEmail", email);
                userInfo.put("UserPassword", password);
                userInfo.put("IsAdmin", false);

                df.set(userInfo);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Failed to Create Account!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void login(Context context, String email, String password) {
        fAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(context, "Login succeeded!", Toast.LENGTH_SHORT).show();
                checkIsAdmin(context);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Failed to Login!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void checkIsAdmin(Context context) {
        DocumentReference df = fStore.collection("Users").document(fAuth.getCurrentUser().getUid());
        df.get().addOnSuccessListener(documentSnapshot -> {
            if (Boolean.TRUE.equals(documentSnapshot.getBoolean("IsAdmin"))) {
//                Intent intent = new Intent(context, Admin.class);
//                startActivity(context, intent, null);
            } else {
//                Intent intent = new Intent(context, User.class);
//                startActivity(context, intent, null);
            }
        });
    }
}
