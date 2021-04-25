package com.naqvi.shopkeeperbudgetdiary.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.naqvi.shopkeeperbudgetdiary.DataBase.DataBaseHelper;
import com.naqvi.shopkeeperbudgetdiary.Models.User;
import com.naqvi.shopkeeperbudgetdiary.Utils.SharedPreference;
import com.naqvi.shopkeeperbudgetdiary.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().hide();

        binding.ltSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String email = binding.etEmail.getText().toString();
        String password = binding.etPassword.getText().toString();

//        email = "m@gmail.com";
//        password = "123";

        if (email.isEmpty() || password.isEmpty()) {
            if (email.isEmpty()) {
                binding.textInputLayoutEmail.setError("Enter your Email");
            } else {
                binding.textInputLayoutEmail.setError(null);
            }

            if (password.isEmpty()) {
                binding.textInputLayoutPassword.setError("Enter your Password");
            } else {
                binding.textInputLayoutPassword.setError(null);
            }
        } else {
            DataBaseHelper db = new DataBaseHelper(LoginActivity.this);
            SharedPreference pref = new SharedPreference(LoginActivity.this);
            User u = new User();
            u.Email = email;
            u.Password = password;
            boolean isExist = db.get_User(u);
            if (isExist) {
                pref.save_Email(email);
                finish();
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Incorrect credentials", Toast.LENGTH_SHORT).show();
            }
        }
    }
}