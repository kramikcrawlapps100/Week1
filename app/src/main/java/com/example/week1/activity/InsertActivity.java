package com.example.week1.activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.week1.AppUtils;
import com.example.week1.MainActivity;
import com.example.week1.MyViewModel;
import com.example.week1.R;
import com.example.week1.model.Employee;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
            name = etName.getText().toString();
            mobileNumber = etMobileNumber.getText().toString();
            email = etEmail.getText().toString();
            address = etAddress.getText().toString();

            if(validation()){
                insertEmployee(name, mobileNumber, email, address);

                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
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
            MyViewModel viewModel = new MyViewModel(sharedPreferences);
            ArrayList<Employee> employeeArrayList = viewModel.getEmployeeList();
            employeeArrayList.add(employee);
            sharedPreferences.edit().putString("employeeList", gson.toJson(employeeArrayList)).apply();
            viewModel.setEmployeeList(employee);
        }

    }

    private boolean validation() {
        if(name.isEmpty()){
            etName.setError("enter valid name");
            return false;
        }
        if(mobileNumber.length() != 10){
            etMobileNumber.setError("enter valid mobile number");
            return false;
        }
        if(!AppUtils.isValidEmail(email)) {
            etEmail.setError("enter valid email");
            return false;
        }
        if(address.isEmpty()){
            etAddress.setError("enter valid address");
            return false;
        }
        return true;
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