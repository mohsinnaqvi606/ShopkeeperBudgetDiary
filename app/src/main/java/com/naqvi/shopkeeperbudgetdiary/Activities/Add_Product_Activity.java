package com.naqvi.shopkeeperbudgetdiary.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.naqvi.shopkeeperbudgetdiary.DataBase.DataBaseHelper;
import com.naqvi.shopkeeperbudgetdiary.Models.Product;
import com.naqvi.shopkeeperbudgetdiary.R;
import com.naqvi.shopkeeperbudgetdiary.Utils.ImageUtil;
import com.naqvi.shopkeeperbudgetdiary.databinding.ActivityAddProductBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Add_Product_Activity extends AppCompatActivity {
    ActivityAddProductBinding binding;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setTitle("Add Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        binding.btnupdateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });

    }


    private void captureImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            } else {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            binding.updateImg1.setImageBitmap(bitmap);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                saveData();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void saveData() {
        String title = binding.etTitle.getEditText().getText().toString();
        String price = binding.etPrice.getEditText().getText().toString();
        String quantity = binding.etQuantity.getEditText().getText().toString();
        String address = binding.etAddress.getEditText().getText().toString();

        if (title.isEmpty() || price.isEmpty() || quantity.isEmpty() || address.isEmpty() || bitmap == null) {
            if (title.isEmpty()) {
                binding.etTitle.setError("Enter product title");
            } else {
                binding.etTitle.setError(null);
            }

            if (price.isEmpty()) {
                binding.etPrice.setError("Enter product price");
            } else {
                binding.etPrice.setError(null);
            }

            if (quantity.isEmpty()) {
                binding.etQuantity.setError("Enter product quantity");
            } else {
                binding.etQuantity.setError(null);
            }

            if (address.isEmpty()) {
                binding.etAddress.setError("Enter address");
            } else {
                binding.etAddress.setError(null);
            }

            if (bitmap == null) {
                Toast.makeText(this, "Please select image", Toast.LENGTH_SHORT).show();
            }

        } else {
            DataBaseHelper db = new DataBaseHelper(Add_Product_Activity.this);
            Product p = new Product();
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat timeFormate = new SimpleDateFormat("hh:mm a");

            p.Image = ImageUtil.convertToBase64(bitmap);
            p.Title = title;
            p.Price = price;
            p.Quantity = quantity;
            p.Date = dateFormat.format(calendar.getTime());
            p.Time = timeFormate.format(calendar.getTime());
            p.Address = address;
            p.Lat = "15.2324";
            p.Lng = "74.233";

            boolean isSaved = db.insert_Product(p);

            if (isSaved) {
                finish();
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }
}