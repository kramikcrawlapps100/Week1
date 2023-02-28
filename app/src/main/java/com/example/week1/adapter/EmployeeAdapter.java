package com.example.week1.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.week1.MainActivity;
import com.example.week1.MyViewModel;
import com.example.week1.R;
import com.example.week1.model.Employee;

import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> implements Filterable {

    Context context;
    ArrayList<Employee> employeeArrayList;
    ArrayList<Employee> employeeArrayListFull;
    ArrayList<Employee> filterList;

    public EmployeeAdapter(Context context, ArrayList<Employee> employeeArrayList) {
        this.context = context;
        this.employeeArrayList = employeeArrayList;
        employeeArrayListFull = new ArrayList<Employee>(this.employeeArrayList);
    }

    /*public void setFilterList(ArrayList<Employee> filterList){
        this.employeeArrayList = filterList;
    }*/
    @NonNull
    @Override
    public EmployeeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_employee, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAdapter.ViewHolder holder, int position) {
    final Employee employee = employeeArrayList.get(position);
    holder.name.setText(employee.getName());
    holder.mobileNumber.setText(employee.getMobileNumber());
    holder.email.setText(employee.getEmail());
    holder.address.setText(employee.getAddress());
    }

    @Override
    public int getItemCount() {
        return employeeArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filterList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(employeeArrayListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Employee employee : employeeArrayListFull) {
                    if (employee.getName().toLowerCase().contains(filterPattern) || employee.getMobileNumber().toLowerCase().contains(filterPattern) || employee.getEmail().toLowerCase().contains(filterPattern) || employee.getAddress().toLowerCase().contains(filterPattern)) {
                        filterList.add(employee);
                    }
                }
            }


            FilterResults results = new FilterResults();
            results.values = filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            employeeArrayList.clear();
            employeeArrayList.addAll((ArrayList<Employee>) results.values);
            if (((ArrayList<?>) results.values).size() == 0) {
                MainActivity.tvNotFound.setVisibility(View.VISIBLE);
            }
            else {
                MainActivity.tvNotFound.setVisibility(View.GONE);
            }
            notifyDataSetChanged();
        }

    };

    public int getFilterListSize() {
        if (filterList == null) {
            return 0;
        }
        else{
            return filterList.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,mobileNumber,email,address;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.rv_name);
            mobileNumber = itemView.findViewById(R.id.rv_mobile_number);
            email = itemView.findViewById(R.id.rv_email);
            address = itemView.findViewById(R.id.rv_address);
        }
    }
}
