package com.adithyasairam.oddi;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class ClassCreatorActivity extends AppCompatActivity {
    static ArrayList<Integer[]> times =  new ArrayList<>();

    private static boolean isStartTime;
    private static int startHour;
    private static int startMinute;
    private static int endHour;
    private static int endMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_creator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save file to file dirs
            }
        });

        final Button startButton = (Button) findViewById(R.id.startTimeButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                isStartTime = true;
                showTimePickerDialog(v);
            }
        });

        Button endButton = (Button) findViewById(R.id.startTimeButton);
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                isStartTime = false;
                showTimePickerDialog(v);
            }
        });

    }
    public void showTimePickerDialog(View v)
    {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if(isStartTime){
                Button startButton = (Button) view.findViewById(R.id.startTimeButton);
                startButton.setText(Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
            }
            else{
                Button endButton = (Button) view.findViewById(R.id.endTimeButton);
                endButton.setText(Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
            }
        }
    }

}
