package com.example.week1.activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.week1.AppUtils;
import com.example.week1.R;
import com.example.week1.model.Employee;
import com.google.gson.Gson;

import java.util.ArrayList;

public class InsertActivity extends AppCompatActivity {

    EditText etName,etMobileNumber,etEmail,etAddress;
    Button btnSubmit;
    SharedPreferences sharedPreferences;

    String name,mobileNumber,email,address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        init();

        btnSubmit.setOnClickListener(v -> {
            name = etEmail.getText().toString();
            mobileNumber = etMobileNumber.getText().toString();
            email = etEmail.getText().toString();
            address = etAddress.getText().toString();

            //validation();

            insertEmployee(name, mobileNumber, email, address);

            finish();
        });
    }

    private void insertEmployee(String name, String mobileNumber, String email, String address) {
        Employee employee = new Employee(name, mobileNumber, email, address);

        Gson gson = new Gson();
        if(sharedPreferences.getString("employeeList", "").equals("")){
            ArrayList<Employee> employeeList = new ArrayList<Employee>();
            employeeList.add(employee);
            String employeeListString = gson.toJson(employeeList);
            sharedPreferences.edit().putString("employeeList", employeeListString).apply();
        }
        else{
            String employeeListString2 = sharedPreferences.getString("employeeList", "");
            ArrayList<Employee> employeeList2;
            employeeList2 = gson.fromJson(employeeListString2, ArrayList.class);
            employeeList2.add(employee);
            sharedPreferences.edit().putString("employeeList", gson.toJson(employeeList2)).apply();
        }

    }

    private void validation() {
        if(name.isEmpty()){
            etName.setError("enter valid name");
            return;
        }
        if(mobileNumber.length() != 10){
            etMobileNumber.setError("enter valid mobile number");
            return;
        }
        if(!AppUtils.isValidEmail(email)) {
            etEmail.setError("enter valid email");
            return;
        }
        if(address.isEmpty()){
            etAddress.setError("enter valid address");
            return;
        }
    }

    private void init() {
        etName = findViewById(R.id.etName);
        etMobileNumber = findViewById(R.id.etMobileNumber);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        btnSubmit = findViewById(R.id.btnSubmit);

        sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
    }
}