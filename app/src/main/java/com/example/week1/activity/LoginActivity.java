package com.example.week1.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import com.example.week1.AppUtils;
import com.example.week1.MainActivity;
import com.example.week1.R;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail,etPassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        btnSignIn.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            if(!AppUtils.isValidEmail(email)) {
                etEmail.setError("enter valid email");
                return;
            }
            if(password.isEmpty()){
                etPassword.setError("enter valid password");
                return;
            }

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }

    private void init() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String email = sh.getString("email", "");
        String password = sh.getString("password", "");


        etEmail.setText(email);
        etPassword.setText(password);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putString("email", etEmail.getText().toString());
        myEdit.putString("password", etPassword.getText().toString());
        myEdit.apply();
    }
}