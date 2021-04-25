package com.naqvi.shopkeeperbudgetdiary.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.naqvi.shopkeeperbudgetdiary.Adapter.Milestone_RecycleView_Adapter;
import com.naqvi.shopkeeperbudgetdiary.Adapter.PurchasedProduct_RecycleView_Adapter;
import com.naqvi.shopkeeperbudgetdiary.DataBase.DataBaseHelper;
import com.naqvi.shopkeeperbudgetdiary.Models.Milestone;
import com.naqvi.shopkeeperbudgetdiary.R;
import com.naqvi.shopkeeperbudgetdiary.databinding.ActivityAddMilestoneBinding;
import com.naqvi.shopkeeperbudgetdiary.databinding.ActivityMilestoneBinding;
import com.naqvi.shopkeeperbudgetdiary.databinding.ActivityPurchasedProductBinding;

import java.util.ArrayList;

public class Milestone_Activity extends AppCompatActivity {
    private ActivityMilestoneBinding binding;
    DataBaseHelper db;
    ArrayList<Milestone> milestonesList = new ArrayList<>();
    Milestone_RecycleView_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMilestoneBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setTitle(R.string.Milestones);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        db = new DataBaseHelper(this);
        milestonesList = db.get_Milestones();

        adapter = new Milestone_RecycleView_Adapter(this, milestonesList);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleView.setAdapter(adapter);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Milestone_Activity.this, Add_Milestone_Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        milestonesList = db.get_Milestones();
        adapter = new Milestone_RecycleView_Adapter(this, milestonesList);
        binding.recycleView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}