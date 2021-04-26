
package com.naqvi.shopkeeperbudgetdiary.Activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.naqvi.shopkeeperbudgetdiary.DataBase.DataBaseHelper;
import com.naqvi.shopkeeperbudgetdiary.Models.Product;
import com.naqvi.shopkeeperbudgetdiary.Models.SellProduct;
import com.naqvi.shopkeeperbudgetdiary.R;
import com.naqvi.shopkeeperbudgetdiary.Utils.SharedPreference;
import com.naqvi.shopkeeperbudgetdiary.databinding.ActivityBuyerFormBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditBuyerForm_Activity extends AppCompatActivity {

    ActivityBuyerFormBinding binding;
    DataBaseHelper db;
    SellProduct p = new SellProduct();
    SharedPreference pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuyerFormBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setTitle(R.string.BuyerForm);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        pref = new SharedPreference(this);
        db = new DataBaseHelper(this);
        p = db.get_SellProductById(pref.get_Id());

        binding.etBuyerName.getEditText().setText(p.BuyerName);
        binding.etBuyerPhoneNo.getEditText().setText(p.BuyerNumber);
        binding.etSellingPrice.getEditText().setText(p.SellingPrice);
        binding.etProductQuantity.getEditText().setText(p.Quantity);

        binding.etBuyerPhoneNo.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String invalidNumber = getResources().getString(R.string.invalidNumber);

                try {

                    Pattern pattern = Pattern.compile("^((\\+92)|(0092)){0,1}\\d{3}-{0,1}\\d{7}$|^\\d{11}$|^\\d{4}-\\d{7}$");
                    Matcher matcher = pattern.matcher(s.toString());
                    boolean res = matcher.matches();

                    if (res) {
                        binding.etBuyerPhoneNo.setError(null);
                    } else {
                        binding.etBuyerPhoneNo.setError(invalidNumber);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

        String buyerName = binding.etBuyerName.getEditText().getText().toString();
        String buyerPhoneNo = binding.etBuyerPhoneNo.getEditText().getText().toString();
        String sellingPrice = binding.etSellingPrice.getEditText().getText().toString();
        String productQuantity = binding.etProductQuantity.getEditText().getText().toString();


        if (buyerName.isEmpty() || buyerPhoneNo.isEmpty() || sellingPrice.isEmpty() || productQuantity.isEmpty() || binding.etBuyerPhoneNo.getError() != null) {
            if (buyerName.isEmpty()) {
                binding.etBuyerName.setError(FieldRequired);
            } else {
                binding.etBuyerName.setError(null);
            }

            if (buyerPhoneNo.isEmpty()) {
                binding.etBuyerPhoneNo.setError(FieldRequired);
            } else {
                binding.etBuyerPhoneNo.setError(null);
            }

            if (sellingPrice.isEmpty()) {
                binding.etSellingPrice.setError(FieldRequired);
            } else {
                binding.etSellingPrice.setError(null);
            }

            if (productQuantity.isEmpty()) {
                binding.etProductQuantity.setError(FieldRequired);
            } else {
                binding.etProductQuantity.setError(null);
            }


        } else {
            Product product = db.get_ProductById(p.ProductID);

            double previousqyt = Double.parseDouble(p.Quantity);
            double currentqyt = Double.parseDouble(productQuantity);
            double productqyt = Double.parseDouble(product.Quantity);

            double rem = (productqyt + previousqyt) - currentqyt;

            double margin = (Double.parseDouble(sellingPrice) / Double.parseDouble(productQuantity)) - Double.parseDouble(product.PerItemPrice);
            double remainingProduct = Double.parseDouble(product.Quantity) - Double.parseDouble(productQuantity);

            if (rem >= 0) {

                product.Quantity = rem + "";
                db.update_Product(product);
                p.BuyerName = buyerName;
                p.BuyerNumber = buyerPhoneNo;
                p.SellingPrice = sellingPrice;
                p.Quantity = productQuantity;
                p.Margin = (margin * Double.parseDouble(productQuantity)) + "";
                int isUpdated = db.update_SoldProduct(p);

                if (isUpdated == 1) {
                    finish();
                } else {
                    Toast.makeText(this, R.string.Somethingwentwrong, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, R.string.QuantityError, Toast.LENGTH_SHORT).show();
            }
        }
    }
}