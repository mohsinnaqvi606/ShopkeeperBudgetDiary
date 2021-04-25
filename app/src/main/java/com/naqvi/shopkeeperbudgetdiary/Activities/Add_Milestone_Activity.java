package com.naqvi.shopkeeperbudgetdiary.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.naqvi.shopkeeperbudgetdiary.DataBase.DataBaseHelper;
import com.naqvi.shopkeeperbudgetdiary.Models.Milestone;
import com.naqvi.shopkeeperbudgetdiary.R;
import com.naqvi.shopkeeperbudgetdiary.databinding.ActivityAddMilestoneBinding;
import com.naqvi.shopkeeperbudgetdiary.databinding.ActivityMilestoneBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Add_Milestone_Activity extends AppCompatActivity {
    private ActivityAddMilestoneBinding binding;
    DatePickerDialog datePicker;
    Milestone m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMilestoneBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setTitle(R.string.AddMilestone);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        m = new Milestone();


        binding.etStartingDate.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

// date picker dialog
                datePicker = new DatePickerDialog(Add_Milestone_Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert,
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
                datePicker = new DatePickerDialog(Add_Milestone_Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert,
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
            if (checkDates(startingDate, endingDate)) {
                DataBaseHelper db = new DataBaseHelper(Add_Milestone_Activity.this);
                m.StartDate = startingDate;
                m.EndDate = endingDate;
                m.TotalPrice = price;
                m.AchievedPrice = "0";
                m.Percentage = "0";
                m.Status = "Incomplete";

                boolean isinserted = db.insert_Milestone(m);
                if (isinserted) {
                    db.calculate_Milestone();
                    finish();
                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Ending Date is smaller than Starting Date", Toast.LENGTH_SHORT).show();
            }
        }
    }

    boolean checkDates(String startingDate, String endingDate) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//        String inputString1 = s.sfrom;
//        String inputString2 = s.eTo;

        try {
            Date date1 = df.parse(startingDate);
            Date date2 = df.parse(endingDate);
            long diff = date2.getTime() - date1.getTime();

            long a = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            m.TotalDays = (a + 1) + "";
            if (diff > 0) {
                return true;
            } else {
                return false;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}