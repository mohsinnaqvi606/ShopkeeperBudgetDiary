
package com.naqvi.shopkeeperbudgetdiary.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.naqvi.shopkeeperbudgetdiary.Adapter.PurchasedProduct_RecycleView_Adapter;
import com.naqvi.shopkeeperbudgetdiary.Adapter.SoldProduct_RecycleView_Adapter;
import com.naqvi.shopkeeperbudgetdiary.DataBase.DataBaseHelper;
import com.naqvi.shopkeeperbudgetdiary.Models.SellProduct;
import com.naqvi.shopkeeperbudgetdiary.R;
import com.naqvi.shopkeeperbudgetdiary.databinding.ActivitySoldProductBinding;

import java.util.ArrayList;

public class SoldProduct_Activity extends AppCompatActivity {

    private ActivitySoldProductBinding binding;
    DataBaseHelper db;
    ArrayList<SellProduct> productList = new ArrayList<>();
    SoldProduct_RecycleView_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySoldProductBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setTitle("Sold product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        db = new DataBaseHelper(this);
        productList = db.get_SellProducts();

        adapter = new SoldProduct_RecycleView_Adapter(this, productList);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        productList = db.get_SellProducts();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }


}