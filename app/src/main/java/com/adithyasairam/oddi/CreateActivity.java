package com.adithyasairam.oddi;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.adithyasairam.oddi.pojos.Assignment;

import java.util.Calendar;

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
        mDateEntryField = (EditText) findViewById(R.id.dateSelection);
        mDateEntryField.addTextChangedListener(mDateEntryWatcher);
        mName = (EditText) findViewById(R.id.nameText);
        mCategoryType = (Spinner) findViewById(R.id.categoryType);
        mCategoryType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = (String)adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mClassType = (Spinner) findViewById(R.id.classType);
        mClassType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                classType = (String)adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private TextWatcher mDateEntryWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String working = s.toString();
            boolean isValid = true;
            if (working.length()==2 && before ==0) {
                if (Integer.parseInt(working) < 1 || Integer.parseInt(working)>12) {
                    isValid = false;
                } else {
                    working+="/";
                    mDateEntryField.setText(working);
                    mDateEntryField.setSelection(working.length());
                }
            }
            else if (working.length()==7 && before ==0) {
                String enteredYear = working.substring(3);
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                if (Integer.parseInt(enteredYear) < currentYear) {
                    isValid = false;
                }
            } else if (working.length()!=7) {
                isValid = false;
            }

            if (!isValid) {
                mDateEntryField.setError("Enter a valid date: MM/YYYY");
            } else {
                mDateEntryField.setError(null);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {}


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    };

}
