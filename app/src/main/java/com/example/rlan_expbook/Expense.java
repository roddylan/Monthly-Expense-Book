/*
 * Expense.java
 *
 * 1.0.0
 *
 * October 1, 2023
 */

package com.example.rlan_expbook;

//import java.text.SimpleDateFormat;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.text.DecimalFormat;

// Expense class
public class Expense implements Parcelable {
    private static final DecimalFormat df = new DecimalFormat("0.00"); // from https://mkyong.com/java/how-to-round-double-float-value-to-2-decimal-points-in-java/
    // use to output charge/cost in correct format
    private String name;
//    private SimpleDateFormat monthStarted;
    private String monthStarted;
    private double charge;
    private String comment;

    public Expense(String name, String monthStarted, double charge, String comment) {
        this.name = name;
        this.monthStarted = monthStarted;
        this.charge = charge;
        this.comment = comment;
    }

    protected Expense(Parcel in) {
        name = in.readString();
        monthStarted = in.readString();
        charge = in.readDouble();
        comment = in.readString();
    }

    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMonthStarted() {
        return monthStarted;
    }

    public void setMonthStarted(String monthStarted) {
        this.monthStarted = monthStarted;
    }

    public double getCharge() {
        return charge;
    }

    public String getStringCharge() {
        // get string rep of charge/cost
        return (df.format(charge) + " CAD");
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(monthStarted);
        dest.writeDouble(charge);
        dest.writeString(comment);
    }
}
