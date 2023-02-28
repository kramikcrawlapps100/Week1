package com.example.week1;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.week1.model.Employee;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public class MyViewModel extends ViewModel {
    private SharedPreferences sharedPreferences;
    Gson gson = new Gson();

    private ArrayList<Employee> employeeList = new ArrayList<>();

    public MyViewModel(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public ArrayList<Employee> getEmployeeList() {
        String employeeListString = sharedPreferences.getString("employeeList", "");
        Type type = new TypeToken<ArrayList<Employee>>(){}.getType();
        employeeList = gson.fromJson(employeeListString, type);
        return employeeList;
    }

    public void setEmployeeList(Employee employee) {
        employeeList = getEmployeeList();
        employeeList.add(employee);
    }
}
