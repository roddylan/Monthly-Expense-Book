/*
 * ExpenseFragment.java
 *
 * 1.0.0
 *
 * October 3, 2023
 */
package com.example.rlan_expbook;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class ExpenseFragment extends Fragment {
    private Expense expense;
    private Button backBtn;
    private Button delBtn;
    private Button editBtn;
    private TextView expenseCost;
    private TextView expenseName;
    private TextView expenseDate;
    private TextView expenseComment;
    private static final String EXPENSE_KEY = "expense_key";

    // https://stackoverflow.com/questions/9931993/passing-an-object-from-an-activity-to-a-fragment
    // handle passing through an expense object to fragment from activity
    public static ExpenseFragment newInstance(Expense expense) {
        ExpenseFragment fragment = new ExpenseFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXPENSE_KEY, expense);
        fragment.setArguments(bundle);

        return fragment;
    }
    private OnExpenseFragmentInteractionListener listener;
    public interface OnExpenseFragmentInteractionListener {
        void onBACKPressed();
        void onDELPressed();
        void onEDITPressed(Expense expense);
    }
    //initialization
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ExpenseFragment.OnExpenseFragmentInteractionListener) {
            listener = (ExpenseFragment.OnExpenseFragmentInteractionListener) context;
        } else {
            throw new RuntimeException((context + "OnExpenseFragmentInteractionListener is not implemented"));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // https://stackoverflow.com/questions/9931993/passing-an-object-from-an-activity-to-a-fragment
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            expense = (Expense) getArguments().getParcelable("EXPENSE_OBJ");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.expense_fragment, container, false);
        backBtn = view.findViewById(R.id.back_button);
        delBtn = view.findViewById(R.id.delete_button);
        editBtn = view.findViewById(R.id.edit_button);
        expenseName = view.findViewById(R.id.charge_name_expense);
        expenseDate = view.findViewById(R.id.charge_date_expense);
        expenseCost = view.findViewById(R.id.charge_cost_expense);
        expenseComment = view.findViewById(R.id.charge_comment_expense);

        expenseName.setText(expense.getName());
        expenseDate.setText(expense.getMonthStarted());
        expenseCost.setText(expense.getStringCharge());
        expenseComment.setText(expense.getComment());


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBACKPressed();

            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEDITPressed(expense);
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDELPressed();
            }
        });
    }


    //    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
}
