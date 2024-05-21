package com.example.btl2.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.btl2.models.Product;
import com.example.btl2.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FirebaseAPI {
    public static FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private static final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    private static final FirebaseStorage fStorage = FirebaseStorage.getInstance();
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
                userData.child("avatar").setValue("images/avatars/default_avatar.png");
                userData.child("isAdmin").setValue(false);

                long MAX_BYTES = 1024 * 1024;
                fStorage.getReference().child("images/avatars/default_avatar.png")
                        .getBytes(MAX_BYTES).addOnSuccessListener(bytes -> {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            User newUser = new User(username, phone, email, password, bitmap, false);
                            taskCompletionSource.setResult(newUser);
                        }).addOnFailureListener(e -> taskCompletionSource.setResult(null));
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
                        User user = new User();
                        user.setEmail(snapshot.child("email").getValue(String.class));
                        user.setUsername(snapshot.child("username").getValue(String.class));
                        user.setPhone(snapshot.child("phone").getValue(String.class));
                        user.setAdmin(Boolean.TRUE.equals(snapshot.child("isAdmin").getValue(Boolean.class)));
                        user.setId(snapshot.getKey());

                        long MAX_BYTES = 1024 * 1024;
                        fStorage.getReference().child("images/avatars/default_avatar.png")
                                .getBytes(MAX_BYTES).addOnSuccessListener(bytes -> {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    user.setAvatar(bitmap);
                                    taskCompletionSource.setResult(user);
                                }).addOnFailureListener(e -> taskCompletionSource.setResult(null));
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

    public static Task<Boolean> checkDuplicateID(String id) {
        TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();
        DatabaseReference products = ref.child("Products");

        products.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean check = false;
                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    if (productSnapshot.getKey() == id) {
                        check = true;
                    }
                }
                taskCompletionSource.setResult(check);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
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

    public static Task<String> getTermsAndConditions() {
        TaskCompletionSource<String> taskCompletionSource = new TaskCompletionSource<>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String ans = snapshot.child("Terms and conditions").getValue(String.class);
                taskCompletionSource.setResult(ans);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                taskCompletionSource.setResult(null);
            }
        });
        return taskCompletionSource.getTask();
    }

    public static void addProduct(Product product) {
        DatabaseReference newProduct = ref.child("Products").child(product.getId());

        newProduct.child("auctionStartTime").setValue(product.getAuctionStartTime());
        newProduct.child("auctionTime").setValue(product.getAuctionTime());
        newProduct.child("currentPrice").setValue(product.getCurrentPrice());
        newProduct.child("description").setValue(product.getDescription());
        newProduct.child("name").setValue(product.getName());
        newProduct.child("owner").setValue(product.getOwner());
        newProduct.child("startPrice").setValue(product.getStartPrice());
        newProduct.child("stepPrice").setValue(product.getStepPrice());

        putImageOnStorage(product);
    }

    public static void putImageOnStorage(Product product) {
        for (int i = 0; i < product.getImage().size(); i++) {
            Bitmap bitmap = product.getImage().get(i);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = fStorage.getReference().child("images").child(product.getId())
                    .child("image" + i + ".jpg").putBytes(data);
        }
    }

    public static Task<Bitmap> getImageFromStorage(String uri) {
        TaskCompletionSource<Bitmap> taskCompletionSource = new TaskCompletionSource<>();
        long MAX_BYTES = 1024 * 1024;
        fStorage.getReferenceFromUrl(uri).getBytes(MAX_BYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                taskCompletionSource.setResult(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                taskCompletionSource.setResult(null);
            }
        });

        return taskCompletionSource.getTask();
    }
}
