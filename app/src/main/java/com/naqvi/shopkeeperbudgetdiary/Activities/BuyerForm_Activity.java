
package com.naqvi.shopkeeperbudgetdiary.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.naqvi.shopkeeperbudgetdiary.DataBase.DataBaseHelper;
import com.naqvi.shopkeeperbudgetdiary.Models.Product;
import com.naqvi.shopkeeperbudgetdiary.Models.SellProduct;
import com.naqvi.shopkeeperbudgetdiary.R;
import com.naqvi.shopkeeperbudgetdiary.Utils.ImageUtil;
import com.naqvi.shopkeeperbudgetdiary.databinding.ActivityAddProductBinding;
import com.naqvi.shopkeeperbudgetdiary.databinding.ActivityBuyerFormBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuyerForm_Activity extends AppCompatActivity {

    ActivityBuyerFormBinding binding;
    String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuyerFormBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        productId = getIntent().getStringExtra("Id");
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setTitle(R.string.BuyerForm);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


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

        String buyerName = binding.etBuyerName.getEditText().getText().toString();
        String buyerPhoneNo = binding.etBuyerPhoneNo.getEditText().getText().toString();
        String sellingPrice = binding.etSellingPrice.getEditText().getText().toString();
        String productQuantity = binding.etProductQuantity.getEditText().getText().toString();

        //  String t = "^((\\+92)|(0092))-{0,1}\d{3}-{0,1}\d{7}$|^\d{11}$|^\d{4}-\d{7}$";
        String pattern = "^((\\+92)|(0092))-{0,1}\\d{3}-{0,1}\\d{7}$|^\\d{11}$|^\\d{4}-\\d{7}$";
        Matcher m;

        Pattern r = Pattern.compile(pattern);
        m = r.matcher(buyerPhoneNo);

        if (m.find()) {
            Toast.makeText(BuyerForm_Activity.this, "MATCH", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(BuyerForm_Activity.this, "NO MATCH", Toast.LENGTH_LONG).show();
        }

        if (buyerName.isEmpty() || buyerPhoneNo.isEmpty() || sellingPrice.isEmpty() || productQuantity.isEmpty()) {
            if (buyerName.isEmpty()) {
                binding.etBuyerName.setError("Enter buyer name");
            } else {
                binding.etBuyerName.setError(null);
            }

            if (buyerPhoneNo.isEmpty()) {
                binding.etBuyerPhoneNo.setError("Enter buyer phoneNo");
            } else {
                binding.etBuyerPhoneNo.setError(null);
            }

            if (sellingPrice.isEmpty()) {
                binding.etSellingPrice.setError("Enter selling price");
            } else {
                binding.etSellingPrice.setError(null);
            }

            if (productQuantity.isEmpty()) {
                binding.etProductQuantity.setError("Enter product qty");
            } else {
                binding.etProductQuantity.setError(null);
            }


        } else {
            DataBaseHelper db = new DataBaseHelper(BuyerForm_Activity.this);
            SellProduct p = new SellProduct();
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat timeFormate = new SimpleDateFormat("hh:mm a");
            Product product = db.get_ProductById(productId);
            double margin = (Double.parseDouble(sellingPrice) / Double.parseDouble(productQuantity)) - Double.parseDouble(product.PerItemPrice);
            double remainingProduct = Double.parseDouble(product.Quantity) - Double.parseDouble(productQuantity);

            if (remainingProduct >= 0) {

                product.Quantity = remainingProduct + "";
                db.update_Product(product);
                p.Image = product.Image;
                p.BuyerName = buyerName;
                p.BuyerNumber = buyerPhoneNo;
                p.Date = dateFormat.format(calendar.getTime());
                p.Time = timeFormate.format(calendar.getTime());
                p.ProductID = productId;
                p.SellingPrice = sellingPrice;
                p.Quantity = productQuantity;
                p.Margin = (margin * Double.parseDouble(productQuantity)) + "";
                boolean isSaved = db.insert_SellProduct(p);

                if (isSaved) {
                    finish();
                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Quantity Error", Toast.LENGTH_SHORT).show();
            }
        }
    }
}