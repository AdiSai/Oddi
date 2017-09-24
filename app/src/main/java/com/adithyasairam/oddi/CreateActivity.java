package com.adithyasairam.oddi;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.adithyasairam.oddi.pojos.Assignment;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CreateActivity extends AppCompatActivity {

    private EditText mName;
    private EditText mDateEntryField;
    private Spinner mCategoryType;
    private Spinner mClassType;

    String category;
    String classType;
    TextView mTextView;


    List<String> phoneNums;
    final int SELECT_PHONE_NUMBER =  1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextView = (TextView) findViewById(R.id.collaboratorTV);
        phoneNums = new ArrayList<>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mName.getText().toString();
                String date = mDateEntryField.getText().toString();
                Assignment assignment = new Assignment(name, category, classType, date);
                Log.d("ASSIGNMENT", assignment.toString());
                Map<String, Assignment> assignmentMap =  new HashMap<>();
                assignmentMap.put(assignment.key(), assignment);
                try
                {
                    FileOutputStream fos = new FileOutputStream("assignment.ser");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(assignmentMap);
                    oos.close();
                    fos.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                //once saved, do the message thing
                StringBuilder phoneNumBuilder = new StringBuilder();
                for (String s : phoneNums) {
                    phoneNumBuilder.append(s + "; ");
                }
                phoneNumBuilder = phoneNumBuilder.delete(phoneNumBuilder.length() - 2, phoneNumBuilder.length());
                Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                i.putExtra("address", phoneNumBuilder.toString());
                i.putExtra("sms_body", "This group chat is for the (X) project");
                i.setType("vnd.android-dir/mms-sms");
                startActivity(i);

            }
        });

        //populate spinners with values
        final Spinner assignmentType = (Spinner) findViewById(R.id.categoryType);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.itemCategories, R.layout.support_simple_spinner_dropdown_item);
        assignmentType.setAdapter(adapter);
        assignmentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedVal = assignmentType.getItemAtPosition(i).toString();
                if ("Project".equals(selectedVal)){
                    LinearLayout collabLayout = (LinearLayout) findViewById(R.id.collaboratorLayout);
                    collabLayout.setVisibility(View.VISIBLE);
                }
                else{
                    LinearLayout collabLayout = (LinearLayout) findViewById(R.id.collaboratorLayout);
                    collabLayout.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        Button button = (Button) findViewById(R.id.dateSelection);
        final Calendar c = Calendar.getInstance();
        button.setText(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + c.get(Calendar.MONTH)+ ", " + c.get(Calendar.YEAR));

        //COLLAB SECTION
        Button collabBtn = (Button) findViewById(R.id.collaboratorButton);
        collabBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(i, SELECT_PHONE_NUMBER);
            }
        });


    }
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.timeline,menu);
        inflater.inflate(R.menu.classes,menu);
        inflater.inflate(R.menu.finalgradecalculator,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    static public class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        @NonNull
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
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);

            button.setText(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + Integer.toString(day)+ ", " + Integer.toString(year));

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_PHONE_NUMBER && resultCode == RESULT_OK) {
            // Get the URI and query the content provider for the phone number
            Uri contactUri = data.getData();
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor cursor = this.getContentResolver().query(contactUri, projection,
                    null, null, null);

            // If the cursor returned is valid, get the phone number
            if (cursor != null && cursor.moveToFirst()) {
                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberIndex);
                // Do something with the phone number
                phoneNums.add(number);
                mTextView.append("\n" + number); //+ Contact Info
            }
            cursor.close();
        }
    }

}

