package com.adithyasairam.oddi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FinalGradeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_grade);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button calculateButton = (Button) findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            EditText currentGrade = (EditText)findViewById(R.id.currentGrade);
                float currentGradeFloat = Float.parseFloat(currentGrade.getText().toString());
            EditText wantedGrade = (EditText)findViewById(R.id.goalClassGrade);
                float wantedGradeFloat = Float.parseFloat(wantedGrade.getText().toString());
            EditText gradeWeight = (EditText)findViewById(R.id.weightGrade);
                float weight = Float.parseFloat(gradeWeight.getText().toString());

            float neededGrade = ((wantedGradeFloat-currentGradeFloat*(100-wantedGradeFloat))/wantedGradeFloat);
                TextView resultantView = (TextView) findViewById(R.id.gradeOutput);
                resultantView.setText(String.valueOf(neededGrade));
            }
        });

    }


}
