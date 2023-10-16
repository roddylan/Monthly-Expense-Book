/*
 * MainActivity.java
 *
 * 1.0.0
 *
 * October 1, 2023
 */

package com.example.rlan_expbook;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements UpdateExpenseFragment.OnFragmentInteractionListener, ExpenseFragment.OnExpenseFragmentInteractionListener{
    private ArrayList<Expense> dataList;
    private ListView expenseList;
    private ArrayAdapter<Expense> expenseAdapter;


    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // setup arraylist and adapter
        dataList = new ArrayList<Expense>();
        expenseList = findViewById(R.id.expense_list);
        expenseAdapter = new ExpenseList(this, dataList);

        expenseList.setAdapter(expenseAdapter);

        final FloatingActionButton addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            new UpdateExpenseFragment().show(getSupportFragmentManager(), "ADD_EXPENSE");
        });

        expenseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment fragment = new ExpenseFragment();
                // https://stackoverflow.com/questions/9931993/passing-an-object-from-an-activity-to-a-fragment
                Expense expense = dataList.get(i);
                position = i;
                Bundle expenseBundle = new Bundle();
                expenseBundle.putParcelable("EXPENSE_OBJ", expense);
                fragment.setArguments(expenseBundle);

//                Bundle positionBundle = new Bundle();
//                positionBundle.putParcelable("POSITION", i);
//                fragment.setArguments(positionBundle);


                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.main_container, fragment, "MAIN_TO_EXPENSE")
                        //.addToBackStack(null)
                        .commit();
//                getSupportFragmentManager().executePendingTransactions();

            }
        });

    }
    @Override
    public void onOKPressed(Expense expense, boolean isAdd) {
        if (isAdd) {
            dataList.add(expense);
            boolean check2 = dataList.get(0) == expense;
        }
        else {
            Expense expense_old = dataList.get(position);
            dataList.set(position, expense);
            boolean check = dataList.get(position) == expense;
            exitExpenseFragment();
        }
        expenseAdapter.notifyDataSetChanged();

        updateTotal();

    }


    public void updateTotal() {
        double totalCharge = 0;
        for (int i = 0; i < dataList.size(); i++) {
            totalCharge += dataList.get(i).getCharge();
        }

        TextView total = findViewById(R.id.charges_total_text);
        String totalChargeString = String.format(Locale.getDefault(), "%.2f", totalCharge) + " CAD"; // https://www.tutorialspoint.com/java/lang/string_format.htm
        total.setText(totalChargeString);
    }
    @Override
    public void onBACKPressed(){
        // https://stackoverflow.com/questions/22474584/remove-old-fragment-from-fragment-manager
//
////        getSupportFragmentManager().beginTransaction()
////                .replace(R.id.expense_container, getSupportFragmentManager().findFragmentById(R.id.main_container), "MAIN_TO_EXPENSE")
////                //.addToBackStack(null)
////                .commit();
        exitExpenseFragment();

    }

    @Override
    public void onDELPressed(){
        dataList.remove(position);
        exitExpenseFragment();
        expenseAdapter.notifyDataSetChanged();
        updateTotal();

    }
    @Override
    public void onEDITPressed(Expense expense){
        new UpdateExpenseFragment(expense).show(getSupportFragmentManager(), "EDIT_EXPENSE");

    }

    private void exitExpenseFragment(){
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("MAIN_TO_EXPENSE");
        if(fragment != null)
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

}