package com.adithyasairam.oddi;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.adithyasairam.oddi.pojos.Class;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ClassCreatorActivity extends AppCompatActivity {
    static ArrayList<Integer[]> times =  new ArrayList<>();
    static ClassCreatorActivity instance;

    private static boolean isStartTime;
    private static int startHour;
    private static int startMinute;
    private static int endHour;
    private static int endMinute;

    EditText mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_creator);
        mName = (EditText)findViewById(R.id.editName);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        instance = this;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mName.getText().toString();
                String start = startHour + ":" + endHour;
                String end = endHour + ":" + endMinute;
                Class clazz =  new Class(name, start, end);
                Log.d("CLASS", clazz.toString());
                Map<String, Class> classMap =  new HashMap<>();
                classMap.put(clazz.key(), clazz);
                try
                {
                    FileOutputStream fos = new FileOutputStream("class.ser");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(classMap);
                    oos.close();
                    fos.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
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

        Button endButton = (Button) findViewById(R.id.endTimeButton);
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
                Button startButton = (Button) instance.findViewById(R.id.startTimeButton);
                startButton.setText(Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
            }
            else if (!isStartTime){
                Button endButton = (Button) instance.findViewById(R.id.endTimeButton);
                endButton.setText(Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
            }
        }
    }
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater inflater3 = getMenuInflater();
        inflater3.inflate(R.menu.timeline,menu);
        inflater3.inflate(R.menu.classes,menu);
        inflater3.inflate(R.menu.finalgradecalculator,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.timeLine:
                Intent intent = new Intent(ClassCreatorActivity.this, TimelineActivity.class);
                startActivity(intent);
                break;
            case R.id.classes:
                break;
            case R.id.FGC:
                Intent intent1 = new Intent (ClassCreatorActivity.this, FinalGradeActivity.class);
                startActivity(intent1);
                break;
            default: break;
        }

        return super.onOptionsItemSelected(item);
    }

}
