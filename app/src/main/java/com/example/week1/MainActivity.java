package com.example.week1;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.week1.activity.InsertActivity;
import com.example.week1.model.Employee;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btnAddEmployee;
    SharedPreferences sharedPreferences;

    ArrayList<Employee> employeeArrayList= new ArrayList<Employee>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        Gson gson = new Gson();
        String employeeListString = sharedPreferences.getString("employeeList", "");
        ArrayList<Employee> employeeList;
        employeeList = gson.fromJson(employeeListString, ArrayList.class);

        Toast.makeText(this, employeeListString, Toast.LENGTH_SHORT).show();

        //Toast.makeText(this, "size:- "+String.valueOf(employeeArrayList.size()), Toast.LENGTH_SHORT).show();
        for(Employee employee : employeeArrayList){
            Log.d("kramik", "name:- " + employee.getName());
        }


        btnAddEmployee.setOnClickListener(v -> {
            Intent intent = new Intent(this, InsertActivity.class);
            startActivity(intent);
        });

    }
    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
        btnAddEmployee = findViewById(R.id.btnAddEmployee);
        sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
    }
}