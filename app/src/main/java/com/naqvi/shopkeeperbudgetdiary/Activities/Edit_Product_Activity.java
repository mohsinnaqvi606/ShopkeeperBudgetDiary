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

public class Edit_Product_Activity extends AppCompatActivity {

    ActivityAddProductBinding binding;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    Bitmap bitmap = null;
    String Id;
    DataBaseHelper db;
    Product p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Id = getIntent().getStringExtra("Id");
        db = new DataBaseHelper(this);
        p = db.get_ProductById(Id);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setTitle(R.string.EditProduct);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        bitmap = ImageUtil.convertToImage(p.Image);
        binding.updateImg1.setImageBitmap(bitmap);
        binding.etTitle.getEditText().setText(p.Title);
        binding.etPrice.getEditText().setText(p.Price);
        binding.etQuantity.getEditText().setText(p.Quantity);
        binding.etAddress.getEditText().setText(p.Address);

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
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, R.string.cameraPermissionDenied, Toast.LENGTH_LONG).show();
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

        String FieldRequired = getResources().getString(R.string.FieldRequired);

        String title = binding.etTitle.getEditText().getText().toString();
        String price = binding.etPrice.getEditText().getText().toString();
        String quantity = binding.etQuantity.getEditText().getText().toString();
        String address = binding.etAddress.getEditText().getText().toString();

        if (title.isEmpty() || price.isEmpty() || quantity.isEmpty() || address.isEmpty() || bitmap == null) {
            if (title.isEmpty()) {
                binding.etTitle.setError(FieldRequired);
            } else {
                binding.etTitle.setError(null);
            }

            if (price.isEmpty()) {
                binding.etPrice.setError(FieldRequired);
            } else {
                binding.etPrice.setError(null);
            }

            if (quantity.isEmpty()) {
                binding.etQuantity.setError(FieldRequired);
            } else {
                binding.etQuantity.setError(null);
            }

            if (address.isEmpty()) {
                binding.etAddress.setError(FieldRequired);
            } else {
                binding.etAddress.setError(null);
            }

            if (bitmap == null) {
                Toast.makeText(this, R.string.PleaseSelectImage, Toast.LENGTH_SHORT).show();
            }

        } else {
            DataBaseHelper db = new DataBaseHelper(Edit_Product_Activity.this);

            double perItemPrice = Double.parseDouble(price) / Double.parseDouble(quantity);

            p.Image = ImageUtil.convertToBase64(bitmap);
            p.Title = title;
            p.Price = price;
            p.Quantity = quantity;
            p.Address = address;
            p.PerItemPrice = perItemPrice + "";

            int isUpdated = db.update_Product(p);

            if (isUpdated == 1) {
                finish();
            } else {
                Toast.makeText(this, R.string.Somethingwentwrong, Toast.LENGTH_SHORT).show();
            }
        }
    }
}