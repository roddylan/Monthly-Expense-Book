/*
 * ExpenseList.java
 *
 * 1.0.0
 *
 * October 1, 2023
 */
package com.example.rlan_expbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

// Custom adapter for Expense objects
public class ExpenseList extends ArrayAdapter<Expense> {
    private ArrayList<Expense> expenses;
    private Context context;

    public ExpenseList(Context context, ArrayList<Expense> expenses) {
        super(context, 0, expenses);
        this.expenses = expenses;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.content, parent, false);
        }

        Expense expense = expenses.get(position);

        TextView expenseName = view.findViewById(R.id.charge_name_list);
        TextView expenseCost = view.findViewById(R.id.charge_cost_list);
        TextView expenseDate = view.findViewById(R.id.charge_date_list);

        expenseName.setText(expense.getName());
        expenseDate.setText(expense.getMonthStarted());
        expenseCost.setText(expense.getStringCharge());

        return view;

    }
}
