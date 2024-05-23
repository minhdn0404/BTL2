package com.example.btl2;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btl2.adapters.ProductImageAdapter;
import com.example.btl2.api.FirebaseAPI;
import com.example.btl2.fragment.BaseActivity;
import com.example.btl2.models.Product;
import com.example.btl2.models.User;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class CreateAuction extends BaseActivity {
    Button addProductPicture;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_PERMISSION = 1;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int ID_LENGTH = 20;
    private static final SecureRandom random = new SecureRandom();
    private final ArrayList<Uri> imageUris = new ArrayList<>();
    private ProductImageAdapter imageAdapter;
    ListView productImages;
    EditText startTimeEditText, endTimeEditText;
    private LocalDateTime startDateTime, endDateTime;
    Button cancelButton, submitButton;
    Boolean isIDUnique;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_auction);
        addProductPicture = findViewById(R.id.addProductPicture);
        productImages = findViewById(R.id.productImages);
        cancelButton = findViewById(R.id.cancelButton);
        submitButton = findViewById(R.id.submitButton);
        startTimeEditText = findViewById(R.id.editTextStartTime);
        endTimeEditText = findViewById(R.id.editTextEndTime);
        imageAdapter = new ProductImageAdapter(this, imageUris);
        productImages.setAdapter(imageAdapter);

        isIDUnique = false;

        addProductPicture.setOnClickListener(v -> {
            imageUris.clear();
            ((TextView)findViewById(R.id.imageError)).setVisibility(View.GONE);
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            }
            loadImages();
        });

        startTimeEditText.setOnClickListener(v -> {
            startTimeEditText.setError(null);
            ((TextView)findViewById(R.id.timeError)).setVisibility(View.GONE);
            getStartTime();
        });

        endTimeEditText.setOnClickListener(v -> {
            endTimeEditText.setError(null);
            ((TextView)findViewById(R.id.timeError)).setVisibility(View.GONE);
            getEndTime();
        });

        cancelButton.setOnClickListener(v -> {
            finish();
        });

        submitButton.setOnClickListener(v -> {
            if(checkValid()) {
                // Tạo một id ngẫu nhiên, kiểm tra xem đã có trên Firebase chưa
                String id = "";
                if (!isIDUnique) {
                    id = generateRandomID();
                    checkID(id);
                }
                String name = ((EditText)findViewById(R.id.productNameEditText)).getText().toString();
                String owner = user.getId();
                String description = ((EditText)findViewById(R.id.productDescriptionEditText)).getText().toString();
                String startTime = startTimeEditText.getText().toString();
                String endTime = endTimeEditText.getText().toString();
                int startPrice = Integer.parseInt(((EditText)findViewById(R.id.startPriceEditText)).getText().toString());
                int currentPrice = 0;
                int stepPrice = Integer.parseInt(((EditText)findViewById(R.id.stepPriceEditText)).getText().toString());

                ArrayList<Bitmap> image = new ArrayList<>();
                for (Uri uri : imageUris) {
                    image.add(getBitmapFromUri(uri));
                }

                // Tạo product từ những thông tin
                Product product = new Product(id, name, owner, description,
                        image,
                        startTime, endTime,
                        startPrice, currentPrice, stepPrice);

                // Đẩy lên Firebase
                FirebaseAPI.addProduct(product);
                finish();
            }
        });
    }

    private Bitmap getBitmapFromUri(Uri uri) {
        InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return BitmapFactory.decodeStream(inputStream);
    }


    private String generateRandomID() {
        StringBuilder sb = new StringBuilder(ID_LENGTH);
        for (int i = 0; i < ID_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    private boolean checkValid() {
        EditText productName = findViewById(R.id.productNameEditText);
        EditText productDescription = findViewById(R.id.productDescriptionEditText);
        EditText startPrice = findViewById(R.id.startPriceEditText);
        EditText stepPrice = findViewById(R.id.stepPriceEditText);
        int numberOfError = 0;
        boolean check = true;

        // Cần có ít nhất 1 hình ảnh
        if (imageUris.isEmpty()) {
            ((TextView)findViewById(R.id.imageError)).setVisibility(View.VISIBLE);
            numberOfError++;
        }

        // Cần điền tên sản phẩm
        if (TextUtils.isEmpty(productName.getText().toString())) {
            productName.setError("Chưa điền nội dung");
            numberOfError++;
        }

        // Cần điền mô tả sản phẩm
        if (TextUtils.isEmpty(productDescription.getText().toString())) {
            productDescription.setError("Chưa điền nội dung");
            numberOfError++;
        }

        // Cần điền thời gian bắt đầu
        if (TextUtils.isEmpty(startTimeEditText.getText().toString())) {
            startTimeEditText.setError("Chưa điền nội dung");
            check = false;
            numberOfError++;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && startDateTime.isBefore(LocalDateTime.now())) { // Nếu thời gian bắt đầu trước thời điểm hiện tại
            startTimeEditText.setError("Thời gian bắt đầu không hợp lệ");
            check = false;
            numberOfError++;
        }

        // Cần điền thời điểm kết thúc
        if (TextUtils.isEmpty(endTimeEditText.getText().toString())) {
            endTimeEditText.setError("Chưa điền nội dung");
            check = false;
            numberOfError++;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && endDateTime.isBefore(LocalDateTime.now())) { // Nếu thời gian kết thúc trước thời điểm hiện tại
            endTimeEditText.setError("Thời gian kết thúc không hợp lệ");
            check = false;
            numberOfError++;
        }

        // Cần điền các loại giá, theo đúng định dạng 'int'
        if (TextUtils.isEmpty(startPrice.getText().toString())) {
            startPrice.setError("Chưa điền nội dung");
            numberOfError++;
        } else {
            try {
                int value = Integer.parseInt(startPrice.getText().toString());
            } catch (NumberFormatException e) {
                startPrice.setError(startPrice.getText().toString() + " không phải số hợp lệ");
                numberOfError++;
            }
        }
        if (TextUtils.isEmpty(stepPrice.getText().toString())) {
            stepPrice.setError("Chưa điền nội dung");
            numberOfError++;
        } else {
            try {
                int value = Integer.parseInt(stepPrice.getText().toString());
            } catch (NumberFormatException e) {
                stepPrice.setError(stepPrice.getText().toString() + " không phải số hợp lệ");
                numberOfError++;
            }
        }

        // Kiểm tra xem startTime có trước endTime không
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && check) {
            if (startDateTime.isAfter(endDateTime)) {
                ((TextView)findViewById(R.id.timeError)).setVisibility(View.VISIBLE);
            } else {
                ((TextView)findViewById(R.id.timeError)).setVisibility(View.GONE);
            }
        }
        return numberOfError == 0;
    }

    private void getStartTime() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view1, hourOfDay, minute) -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startDateTime = LocalDateTime.of(year, month + 1, dayOfMonth, hourOfDay, minute);
                }
                updateStartTimeEditText();
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
            timePickerDialog.show();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void updateStartTimeEditText() {
        String formattedDateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm  dd-MM-yyyy");
            formattedDateTime = startDateTime.format(formatter);
        }
        startTimeEditText.setText(formattedDateTime);
    }

    private void getEndTime() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view1, hourOfDay, minute) -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    endDateTime = LocalDateTime.of(year, month + 1, dayOfMonth, hourOfDay, minute);
                }
                updateEndTimeEditText();
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
            timePickerDialog.show();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void updateEndTimeEditText() {
        String formattedDateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm  dd-MM-yyyy");
            formattedDateTime = endDateTime.format(formatter);
        }
        endTimeEditText.setText(formattedDateTime);
    }

    private void loadImages() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            assert data != null;
            if (data.getClipData() != null) { // Multiple images selected
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    imageUris.add(imageUri);
                }
            } else if (data.getData() != null) { // Single image selected
                Uri imageUri = data.getData();
                imageUris.add(imageUri);
            }
            imageAdapter.notifyDataSetChanged();
        }
    }

    private class GetRandomID extends AsyncTask<String, Void, Boolean> {
        protected ProgressDialog dialog;
        protected Context context;

        public GetRandomID(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Đang tạo sản phẩm");
            this.dialog.setCancelable(false);
            this.dialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                Task<Boolean> task = FirebaseAPI.checkDuplicateID((String) params[0]);
                isIDUnique = Tasks.await(task);
                return isIDUnique;
            } catch (Exception e) {
                Log.v("ASYNC", "ERROR : " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            // Đăng ký OnFieldSelectedListener cho adapter
            isIDUnique = result;
            if (dialog.isShowing())
                dialog.dismiss();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void checkID(String id) {
        GetRandomID taskGetUser = new GetRandomID(this);
        taskGetUser.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, id);
        new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... strings) {
                try {
                    return taskGetUser.get();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                isIDUnique = result;
            }
        }.execute();
    }
}