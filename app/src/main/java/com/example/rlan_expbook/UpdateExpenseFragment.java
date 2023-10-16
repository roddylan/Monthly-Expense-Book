/*
 * UpdateExpenseFragment.java
 *
 * 1.0.0
 *
 * October 1, 2023
 */
package com.example.rlan_expbook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class UpdateExpenseFragment extends DialogFragment {

    private EditText expenseCost;
    private EditText expenseName;
    private EditText expenseDate;
    private EditText expenseComment;

    final private boolean isAdd; // to determine what type of update the dialog does (edit vs add)
    private Expense expense;

    private OnFragmentInteractionListener listener;
    public interface OnFragmentInteractionListener {
        void onOKPressed(Expense expense, boolean isAdd);
    }

    // constructors for adding and editing
    public UpdateExpenseFragment() {
        // called when adding a new Expense object
        this.expense = null; // not yet created, form must be finished + submitted first
        this.isAdd = true;

    }
    public UpdateExpenseFragment(Expense expense) {
        this.expense = expense;
        this.isAdd = false;
    }

    // initialization
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException((context + "OnFragmentInteractionListener is not implemented"));
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.update_expense_fragment_layout, null);

        expenseCost = view.findViewById(R.id.expense_cost_edit_text);
        expenseName = view.findViewById(R.id.expense_name_edit_text);
        expenseDate = view.findViewById(R.id.expense_date_edit_text);
        expenseComment = view.findViewById(R.id.expense_comment_edit_text);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("Edit/Add Expense")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String chargeString = expenseCost.getText().toString();
                        String name = expenseName.getText().toString();
                        String monthStarted = expenseDate.getText().toString();
                        String comment = expenseComment.getText().toString();

                        // input validation
                        // required fields
                        boolean isError = false;
                        if (name.isEmpty()) {
                            if(isAdd) {
                                isError = true;
                                expenseName.setError("Required");
                            }

                        }
                        if (chargeString.isEmpty()) {
                            if(isAdd){
                                isError = true;
                                expenseCost.setError("Required");
                            }
                        }
                        if (monthStarted.isEmpty()) {
                            if(isAdd){
                                isError = true;
                                expenseDate.setError("Required");
                            }
                        }
                        else { // check date formatting
                            String[] dateSplit = monthStarted.split("-");

                            if (dateSplit.length != 2 || Integer.parseInt(dateSplit[1]) > 12 || dateSplit[1].length() != 2 || dateSplit[0].length() != 4) {
                                isError = true;
                                expenseDate.setError("Incorrect format");
                            } else if (Integer.parseInt(dateSplit[0]) < 1900) { // TODO: add upper bound
                                isError = true;
                                expenseDate.setError("Out of Range");
                            }

                        }
                        if (!isError) {
                            double charge = 0;
                            if (!chargeString.isEmpty()) {
                                charge = Double.parseDouble(chargeString); // need to do this after we verify user inputted a cost, otherwise wont work
                            }
                            if (isAdd) {
                                // if adding expense
                                expense = new Expense(name, monthStarted, charge, comment);
                                listener.onOKPressed(expense, true);
                            } else {
                                if (!monthStarted.isEmpty()) {
                                    expense.setMonthStarted(monthStarted);
                                }
                                if (!chargeString.isEmpty()) {
                                    expense.setCharge(charge);
                                }
                                if (!comment.isEmpty()){
                                    expense.setComment(comment);
                                }
                                if (!name.isEmpty()) {
                                    expense.setName(name);
                                }
                                listener.onOKPressed(expense, false);
                            }
                        }


                    }
                }).create();
    }
}
