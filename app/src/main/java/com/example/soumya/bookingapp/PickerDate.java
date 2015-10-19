package com.example.soumya.bookingapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;


/**
 * Created by Soumya on 8/27/2015.
 */
public class PickerDate extends DialogFragment implements DatePickerDialog.OnDateSetListener {

//    private final View view;
    int year,day,month;
    SharedPreferences prefs;
    public static final String MyPreferences="Prefs";
    SharedPreferences.Editor editor;

    public PickerDate(View view){

    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);


    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    String date=dayOfMonth+"-"+(monthOfYear+1)+"-"+year;

        prefs= PreferenceManager.getDefaultSharedPreferences(getActivity());

        editor = prefs.edit();
        editor.putString("date", date);
        editor.commit();

        Intent intent=new Intent(view.getContext(),timeSlots.class);
        startActivity(intent);



    }


}
