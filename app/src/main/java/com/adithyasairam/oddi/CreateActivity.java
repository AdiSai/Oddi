package com.adithyasairam.oddi;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.adithyasairam.oddi.pojos.Assignment;

import java.util.Calendar;
import java.util.Locale;

public class CreateActivity extends AppCompatActivity {

    private EditText mName;
    private EditText mDateEntryField;
    private Spinner mCategoryType;
    private Spinner mClassType;

    String category;
    String classType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mName.getText().toString();
                String date = mDateEntryField.getText().toString();
                Assignment assignment = new Assignment(name, category, classType, date);
                Log.d("ASSIGNMENT", assignment.toString());
            }
        });

        //populate spinners with values
        Spinner assignmentType = (Spinner) findViewById(R.id.classType);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.itemCategories, R.layout.support_simple_spinner_dropdown_item);
        assignmentType.setAdapter(adapter);


        Button button = (Button) findViewById(R.id.dateSelection);
        final Calendar c = Calendar.getInstance();
        button.setText(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + c.get(Calendar.MONTH)+ ", " + c.get(Calendar.YEAR));

    }


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    static public class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            final Calendar c = Calendar.getInstance();
            Button button = (Button) getActivity().findViewById(R.id.dateSelection);
            int selectedYear = view.getYear();
            int selectedMonth = view.getMonth();
            int selectedDay = view.getDayOfMonth();
            c.set(Calendar.YEAR, selectedYear);
            c.set(Calendar.MONTH, selectedMonth);
            c.set(Calendar.DAY_OF_MONTH, selectedDay);

            button.setText(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + c.get(Calendar.MONTH)+ ", " + c.get(Calendar.YEAR));

        }
    }


}

