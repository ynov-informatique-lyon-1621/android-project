package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.utils.Constant;


public class DatePickerFragment extends DialogFragment {

    public static DatePickerFragment newInstance(int date_field_id) {

        Bundle args = new Bundle();
        args.putInt("date_field_id",date_field_id);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this.dateChangedListener, year, month, day);
    }

    protected DatePickerDialog.OnDateSetListener dateChangedListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            EditText dateIpt = getActivity().findViewById(getArguments().getInt("date_field_id"));
            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month,dayOfMonth);
            if(dateIpt != null) {
                dateIpt.setText(Constant.dateFormat.format(calendar.getTime()));
            }
        }


    };


}
