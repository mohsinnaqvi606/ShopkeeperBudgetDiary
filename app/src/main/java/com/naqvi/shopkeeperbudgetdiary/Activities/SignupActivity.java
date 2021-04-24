package com.naqvi.shopkeeperbudgetdiary.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.naqvi.shopkeeperbudgetdiary.DataBase.DataBaseHelper;
import com.naqvi.shopkeeperbudgetdiary.MainActivity;
import com.naqvi.shopkeeperbudgetdiary.Models.User;
import com.naqvi.shopkeeperbudgetdiary.R;
import com.naqvi.shopkeeperbudgetdiary.Utils.SharedPreference;
import com.naqvi.shopkeeperbudgetdiary.databinding.ActivityLoginBinding;
import com.naqvi.shopkeeperbudgetdiary.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().hide();

        binding.ltLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterUser();
            }
        });
    }

    private void RegisterUser() {
        String name = binding.textInputLayoutFullname.getEditText().getText().toString();
        String email = binding.textInputLayoutEmail.getEditText().getText().toString();
        String password = binding.textInputLayoutPassword.getEditText().getText().toString();
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {


            if (name.isEmpty()) {
                binding.textInputLayoutFullname.setError("Enter your Name");
            } else {
                binding.textInputLayoutFullname.setError(null);
            }

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
            DataBaseHelper db = new DataBaseHelper(SignupActivity.this);
            SharedPreference pref = new SharedPreference(SignupActivity.this);
            User u = new User();
            u.Name = name;
            u.Email = email;
            u.Password = password;
            boolean res = db.insert_User(u);
            if (res) {
                pref.save_Email(email);
                finish();
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        }
    }
}