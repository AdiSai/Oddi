package com.adithyasairam.oddi;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CollabActivity extends AppCompatActivity {
    TextView mTextView;
    Button mButton;
    List<String> phoneNums;
    final int SELECT_PHONE_NUMBER =  1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //START GC
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
                        }).show();
            }
        });
        phoneNums = new ArrayList<>();
        mTextView = (TextView)findViewById(R.id.textView);
        mButton = (Button)findViewById(R.id.buttonAdd);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(i, SELECT_PHONE_NUMBER);
            }
        });
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
