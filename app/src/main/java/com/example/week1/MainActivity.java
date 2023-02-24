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

import com.example.week1.activity.InsertActivity;
import com.example.week1.model.Employee;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btnAddEmployee;
    SharedPreferences sharedPreferences;
    int id =0;

    ArrayList<Employee> employeeArrayList= new ArrayList<Employee>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        int lastIndex = sharedPreferences.getInt("id",0);

        for(int i = 1;i <= lastIndex; i++) {
            String dataString = sharedPreferences.getString("employee_"+i,"");
            Log.d("kramik", "dataString:- "+dataString);
            Gson gson = new Gson();
            Employee employee = gson.fromJson(dataString, Employee.class);
            employeeArrayList.add(employee);
        }

        Log.d("kramik", "getList:- "+ employeeArrayList.toString());


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