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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CreateActivity extends AppCompatActivity {

    private EditText mName;
    private Button mDateEntryField;
    private Spinner mCategoryType;
    private Spinner mClassType;

    final static Calendar c = Calendar.getInstance();

    String category;
    String classType;
    TextView mTextView;
    ArrayList<Assignment> assignmentList;
    List<String> phoneNums;
    final int SELECT_PHONE_NUMBER =  1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextView = (TextView) findViewById(R.id.collaboratorTV);
        mDateEntryField = (Button) findViewById(R.id.dateSelection);
        phoneNums = new ArrayList<>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //input verification
                mName = (EditText) findViewById(R.id.nameText);
                boolean isNameEmpty = (null == mName.getText() || "".equals(mName.getText()));

                Button dateButton = (Button) findViewById(R.id.dateSelection);
                boolean isInvalidDate = (dateButton.getText().toString().equals("DATE"));


                boolean isAnyPointEmpty = isNameEmpty || isInvalidDate;
                if(!isAnyPointEmpty) {
                    String name = mName.getText().toString();
                    String date = mDateEntryField.getText().toString();
                    category = mCategoryType.getSelectedItem().toString();
                    classType = mClassType.getSelectedItem().toString();
                    Assignment assignment = new Assignment(name, category, classType, date);
                    Log.d("ASSIGNMENT", assignment.toString());
                    assignmentList = new ArrayList<>();
                    Map<String, Assignment> assignmentMap = new HashMap<>();
                    for (Assignment a : assignmentList) { assignmentMap.put(a.key(), a); }
                    assignmentMap.put(assignment.key(), assignment);
                    try {
                        File file = new File(OddiApp.getInternalDataDir().getAbsolutePath(), "assignment.ser");
                        FileOutputStream fos = new FileOutputStream(file);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(assignmentMap);
                        oos.close();
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //once saved, do the message thing
                    if(!phoneNums.isEmpty()) {
                        StringBuilder phoneNumBuilder = new StringBuilder();
                        for (String s : phoneNums) {
                            phoneNumBuilder.append(s + "; ");
                        }
                        phoneNumBuilder = phoneNumBuilder.delete(phoneNumBuilder.length() - 2, phoneNumBuilder.length());
                        Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                        i.putExtra("address", phoneNumBuilder.toString());
                        i.putExtra("sms_body", "This group chat is for the " + category + " project.");
                        i.setType("vnd.android-dir/mms-sms");
                        startActivity(i);
                    }
                }
            }
        });

        //populate spinners with values
        mCategoryType = (Spinner) findViewById(R.id.categoryType);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.itemCategories, R.layout.support_simple_spinner_dropdown_item);
        mCategoryType.setAdapter(adapter);
        mCategoryType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedVal = mCategoryType.getItemAtPosition(i).toString();
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

        //inits ubtton text
        Button button = (Button) findViewById(R.id.dateSelection);
        button.setText("DATE");

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
        try {
            File file = new File(OddiApp.getInternalDataDir().getAbsolutePath(), "class.ser");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Map<String, Class> classMap = (Map<String, Class>) ois.readObject();
            ArrayList<Class> classList = new ArrayList<Class>(classMap.values());

            File file2 = new File(OddiApp.getInternalDataDir().getAbsolutePath(), "assignment.ser");

            if(file2.exists()) {
                FileInputStream fis2 = new FileInputStream(file2);
                ObjectInputStream ois2 = new ObjectInputStream(fis2);
                Map<String, Assignment> assignmentMap = (Map<String, Assignment>) ois2.readObject();
                assignmentList = new ArrayList<Assignment>(assignmentMap.values());
            }
            else{
                assignmentList = new ArrayList<>(0);
            }

            mClassType = (Spinner) findViewById(R.id.classType);
            ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, classList.toArray());
            mClassType.setAdapter(adapter2);

        } catch (Exception e) {
            e.printStackTrace();
        }

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

