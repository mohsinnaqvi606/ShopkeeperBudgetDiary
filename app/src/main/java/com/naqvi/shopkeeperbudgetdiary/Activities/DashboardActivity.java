package com.naqvi.shopkeeperbudgetdiary.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.naqvi.shopkeeperbudgetdiary.DataBase.DataBaseHelper;
import com.naqvi.shopkeeperbudgetdiary.R;
import com.naqvi.shopkeeperbudgetdiary.databinding.ActivityDashboardBinding;
import com.naqvi.shopkeeperbudgetdiary.databinding.ActivitySignupBinding;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setTitle(R.string.Dashboard);

        db = new DataBaseHelper(this);

        binding.tvtotal.setText(""+db.get_Products().size());
        binding.tvsold.setText(""+db.get_SellProducts().size());
        binding.tvmilestone.setText(""+db.get_Milestones().size());

        binding.productsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, Purchased_Product_Activity.class);
                startActivity(intent);
            }
        });

        binding.soldCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, SoldProduct_Activity.class);
                startActivity(intent);
            }
        });

        binding.milestoneCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, Milestone_Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = new DataBaseHelper(this);

        binding.tvtotal.setText(""+db.get_Products().size());
        binding.tvsold.setText(""+db.get_SellProducts().size());
        binding.tvmilestone.setText(""+db.get_Milestones().size());
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                finish();
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                break;
        }
        return true;
    }
}