package com.naqvi.shopkeeperbudgetdiary.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.naqvi.shopkeeperbudgetdiary.Adapter.PurchasedProduct_RecycleView_Adapter;
import com.naqvi.shopkeeperbudgetdiary.DataBase.DataBaseHelper;
import com.naqvi.shopkeeperbudgetdiary.Models.Product;
import com.naqvi.shopkeeperbudgetdiary.R;
import com.naqvi.shopkeeperbudgetdiary.databinding.ActivityPurchasedProductBinding;

import java.util.ArrayList;

public class Purchased_Product_Activity extends AppCompatActivity {

    private ActivityPurchasedProductBinding binding;
    DataBaseHelper db;
    ArrayList<Product> products_list = new ArrayList<>();
    PurchasedProduct_RecycleView_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPurchasedProductBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setTitle("Purchased product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        db = new DataBaseHelper(this);
        products_list = db.get_Products();

        adapter = new PurchasedProduct_RecycleView_Adapter(this, products_list);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleView.setAdapter(adapter);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Purchased_Product_Activity.this, Add_Product_Activity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        products_list = db.get_Products();
        adapter = new PurchasedProduct_RecycleView_Adapter(this, products_list);
        binding.recycleView.setAdapter(adapter);
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