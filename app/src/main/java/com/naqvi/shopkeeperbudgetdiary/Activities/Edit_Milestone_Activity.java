package com.naqvi.shopkeeperbudgetdiary.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.naqvi.shopkeeperbudgetdiary.DataBase.DataBaseHelper;
import com.naqvi.shopkeeperbudgetdiary.Models.Milestone;
import com.naqvi.shopkeeperbudgetdiary.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Edit_Milestone_Activity extends AppCompatActivity {

    private com.naqvi.shopkeeperbudgetdiary.databinding.ActivityAddMilestoneBinding binding;
    DatePickerDialog datePicker;
    DataBaseHelper db;
    Milestone milestone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.naqvi.shopkeeperbudgetdiary.databinding.ActivityAddMilestoneBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setTitle("Add Milestone");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        db = new DataBaseHelper(this);
        milestone = db.get_MilestoneById(getIntent().getStringExtra("Id"));
        milestone.ID = Integer.parseInt(getIntent().getStringExtra("Id"));

        binding.etStartingDate.getEditText().setText(milestone.StartDate);
        binding.etEndingDate.getEditText().setText(milestone.EndDate);
        binding.etTotalPrice.getEditText().setText(milestone.TotalPrice);


        binding.etStartingDate.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

// date picker dialog
                datePicker = new DatePickerDialog(Edit_Milestone_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
// eText.setText(dayOfMonth + "/" + (month) + "/" + year);
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.YEAR, year);
                                c.set(Calendar.MONTH, monthOfYear);
                                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                                String f = df.format(c.getTime());
                                binding.etStartingDate.getEditText().setText(f);
                            }
                        }, year, month, day);
                datePicker.show();
            }
        });


        binding.etEndingDate.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

// date picker dialog
                datePicker = new DatePickerDialog(Edit_Milestone_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
// eText.setText(dayOfMonth + "/" + (month) + "/" + year);
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.YEAR, year);
                                c.set(Calendar.MONTH, monthOfYear);
                                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                                String f = df.format(c.getTime());
                                binding.etEndingDate.getEditText().setText(f);
                            }
                        }, year, month, day);
                datePicker.show();
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

    void saveData() {
        String startingDate = binding.etStartingDate.getEditText().getText().toString();
        String endingDate = binding.etEndingDate.getEditText().getText().toString();
        String price = binding.etTotalPrice.getEditText().getText().toString();
        if (startingDate.isEmpty() || endingDate.isEmpty() || price.isEmpty()) {

            if (startingDate.isEmpty()) {
                binding.etStartingDate.setError("Enter starting date");
            } else {
                binding.etStartingDate.setError(null);
            }

            if (endingDate.isEmpty()) {
                binding.etEndingDate.setError("Enter ending date");
            } else {
                binding.etEndingDate.setError(null);
            }

            if (price.isEmpty()) {
                binding.etTotalPrice.setError("Enter Price");
            } else {
                binding.etTotalPrice.setError(null);
            }

        } else {
            DataBaseHelper db = new DataBaseHelper(Edit_Milestone_Activity.this);


            milestone.StartDate = startingDate;
            milestone.EndDate = endingDate;
            milestone.TotalDays = "";
            milestone.TotalPrice = price;
            milestone.AchievedPrice = "0";
            milestone.Percentage = "0";
            milestone.Status = "Incomplete";

            int isinserted = db.update_Milestone(milestone);
            if (isinserted == 1) {
                finish();
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }
}