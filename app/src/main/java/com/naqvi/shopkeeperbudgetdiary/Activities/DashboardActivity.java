package com.naqvi.shopkeeperbudgetdiary.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.naqvi.shopkeeperbudgetdiary.DataBase.DataBaseHelper;
import com.naqvi.shopkeeperbudgetdiary.R;
import com.naqvi.shopkeeperbudgetdiary.Utils.SharedPreference;
import com.naqvi.shopkeeperbudgetdiary.databinding.ActivityDashboardBinding;
import com.naqvi.shopkeeperbudgetdiary.databinding.ActivitySignupBinding;

import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;
    DataBaseHelper db;
    SharedPreference pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setTitle(R.string.Dashboard);

        db = new DataBaseHelper(this);
        pref = new SharedPreference(this);

        binding.tvtotal.setText("" + db.get_Products().size());
        binding.tvsold.setText("" + db.get_SellProducts().size());
        binding.tvmilestone.setText("" + db.get_Milestones().size());

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

        binding.tvtotal.setText("" + db.get_Products().size());
        binding.tvsold.setText("" + db.get_SellProducts().size());
        binding.tvmilestone.setText("" + db.get_Milestones().size());
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
            case R.id.language:
                changeLanguage();
                break;
        }
        return true;
    }

    void changeLanguage() {

        String Arabic = getResources().getString(R.string.Arabic);

        AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
        builder.setTitle(R.string.SelectLanguage);
        final CharSequence[] options = {"English", Arabic};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    dialog.dismiss();
                    pref.save_Language("en");
                    setLanguage("en");
                } else if (item == 1) {
                    dialog.dismiss();
                    pref.save_Language("ar");
                    setLanguage("ar");
                }
            }
        });

        builder.create().show();
    }

    void restartActivity() {
        finish();
        Intent intent = new Intent(DashboardActivity.this, DashboardActivity.class);
        startActivity(intent);
    }

    void setLanguage(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
        getResources().updateConfiguration(config,
                getResources().getDisplayMetrics());

        restartActivity();
    }
}