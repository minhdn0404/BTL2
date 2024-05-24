package com.example.btl2.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.btl2.MainActivity;
import com.example.btl2.R;
import com.example.btl2.api.FirebaseAPI;
import com.example.btl2.databinding.FragmentMeBinding;
import com.example.btl2.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MeFragment extends Fragment {
    private FragmentMeBinding binding;
    private User user;
    private Uri imageUri;
    private Bitmap bitmap;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private DatabaseReference usersRef;


    public MeFragment(User user) {
        this.user = user;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        mAuth = FirebaseAuth.getInstance();
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
//        storageReference = FirebaseStorage.getInstance().getReference().child("images");

        // Storage
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        // Realtime
        firebaseDatabase = FirebaseDatabase.getInstance();
        usersRef = firebaseDatabase.getReference("Users");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMeBinding.inflate(inflater, container, false);

        initUI();
        initListener();

        return binding.getRoot();
    }

    private void initUI() {
        binding.meAvartarImg.setImageBitmap(user.getAvatar());
        binding.textView.setText(user.getUsername());
        binding.textView2.setText(user.getEmail());
        binding.editTextMeUsername.setText(user.getUsername());
        binding.editTextMePhone.setText(user.getPhone());
        binding.textViewMeEmail.setText(user.getEmail());
    }

    private void initListener() {
        // Update ảnh
        binding.meAvatarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });

        binding.meSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserInfo(binding.editTextMeUsername.getText().toString().trim(),
                        binding.editTextMePhone.getText().toString().trim());
            }
        });
    }

    private void updateUserInfo(String newUsername, String newPhone) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("username", newUsername);
        updates.put("phone", newPhone);
        user.setPhone(newPhone);
        user.setUsername(newUsername);

        // Reference the specific user node
        DatabaseReference userRef = usersRef.child(user.getId());

        // Update the user information
        userRef.updateChildren(updates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Data updated successfully
                        Toast.makeText(getContext(), "User info updated", Toast.LENGTH_SHORT).show();
                        Log.d(user.getId(), "User Id:");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to update data
                        Toast.makeText(getContext(), "Failed to update user info", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateUserInfo(String newAvatar) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("avatar", newAvatar);

        // Reference the specific user node
        DatabaseReference userRef = usersRef.child(user.getId());

        // Update the user information
        userRef.updateChildren(updates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Data updated successfully
                        Toast.makeText(getContext(), "User avatar updated", Toast.LENGTH_SHORT).show();
                        Log.d(user.getId(), "User Id:");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to update data
                        Toast.makeText(getContext(), "Failed to update user avatar", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() !=null) {
            imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), imageUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            user.setAvatar(bitmap);
            binding.meAvartarImg.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture() {
        final String randomKey = UUID.randomUUID().toString();

        StorageReference riverRef = storageReference.child("images/avatars/" + randomKey);
        updateUserInfo("images/avatars/" + randomKey);

        riverRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }


}