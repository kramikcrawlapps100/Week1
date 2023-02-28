package com.example.week1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.week1.activity.InsertActivity;
import com.example.week1.adapter.EmployeeAdapter;
import com.example.week1.model.Employee;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btnAddEmployee;
    SharedPreferences sharedPreferences;
    ArrayList<Employee> employeeArrayList= new ArrayList<>();
    EmployeeAdapter adapter;

    Gson gson = new Gson();
    SearchView searchView;
    public static TextView tvNotFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        try {
            getEmployeeList();
            setRecyclerView(employeeArrayList);
        }catch (Exception e) {
            e.printStackTrace();
        }

        goToInsertActivity();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //filterList(query);
                //adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                adapter.getFilter().filter(text);
                return false;
            }
        });
    }

    private void goToInsertActivity() {
        btnAddEmployee.setOnClickListener(v -> {
            Intent intent = new Intent(this, InsertActivity.class);
            startActivityForResult(intent, 2);
        });
    }

    private void getEmployeeList() {
        MyViewModel viewModel = new MyViewModel(sharedPreferences);
        employeeArrayList = viewModel.getEmployeeList();
    }

    private void setRecyclerView(ArrayList<Employee> employees) {
        if (employeeArrayList.isEmpty()){
            Toast.makeText(this, "Employee list is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        adapter = new EmployeeAdapter(this,employees);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
        btnAddEmployee = findViewById(R.id.btnAddEmployee);
        sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        searchView = findViewById(R.id.searchView);
        tvNotFound = findViewById(R.id.tvNotFound);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 2 && resultCode == RESULT_OK) {
                getEmployeeList();
                setRecyclerView(employeeArrayList);
            }
        } catch (Exception ex) {
            Log.d("kramik", ex.toString());
        }
    }

    public void filterList(String text){
        ArrayList<Employee> filterList = new ArrayList<>();
        for (Employee employee : employeeArrayList) {
            if (employee.getName().toLowerCase().contains(text.toLowerCase()) || employee.getMobileNumber().toLowerCase().contains(text.toLowerCase()) || employee.getEmail().toLowerCase().contains(text.toLowerCase()) || employee.getAddress().toLowerCase().contains(text.toLowerCase())) {
                filterList.add(employee);
            }
        }

        if(filterList.isEmpty()) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }
        else{
            //adapter.setFilterList(filterList);
            adapter.notifyDataSetChanged();
        }
    }
}