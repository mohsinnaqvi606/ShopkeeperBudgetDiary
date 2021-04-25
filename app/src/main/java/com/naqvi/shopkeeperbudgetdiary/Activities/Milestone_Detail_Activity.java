package com.naqvi.shopkeeperbudgetdiary.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.naqvi.shopkeeperbudgetdiary.DataBase.DataBaseHelper;
import com.naqvi.shopkeeperbudgetdiary.Models.Milestone;
import com.naqvi.shopkeeperbudgetdiary.R;
import com.naqvi.shopkeeperbudgetdiary.Utils.SharedPreference;
import com.naqvi.shopkeeperbudgetdiary.databinding.ActivityMilestoneBinding;
import com.naqvi.shopkeeperbudgetdiary.databinding.ActivityMilestoneDetailBinding;

public class Milestone_Detail_Activity extends AppCompatActivity {

    private ActivityMilestoneDetailBinding binding;
    DataBaseHelper db;
    Milestone m;
    SharedPreference pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMilestoneDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setTitle("Milestone Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        pref = new SharedPreference(this);
        String id = pref.get_Id();
        db = new DataBaseHelper(this);
        m = db.get_MilestoneById(id);


        binding.tvStartingDate.setText(m.StartDate);
        binding.tvEndingDate.setText(m.EndDate);
        binding.tvTotalDays.setText(m.TotalDays);
        binding.tvTotalPrice.setText(m.TotalPrice);
        binding.tvPercentage.setText(m.Percentage);
        binding.tvAchievedPrice.setText(m.AchievedPrice);
        binding.tvStatus.setText(m.Status);
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