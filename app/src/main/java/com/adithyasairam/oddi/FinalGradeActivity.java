package com.adithyasairam.oddi;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
        //setSupportActionBar(toolbar);
        Button calculateButton = (Button) findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            EditText currentGrade = (EditText)findViewById(R.id.currentGrade);
                float currentGradeFloat = (Float.parseFloat(currentGrade.getText().toString())/100);
            EditText wantedGrade = (EditText)findViewById(R.id.goalClassGrade);
                float wantedGradeFloat = (Float.parseFloat(wantedGrade.getText().toString())/100);
            EditText gradeWeight = (EditText)findViewById(R.id.weightGrade);
                float weight = (Float.parseFloat(gradeWeight.getText().toString())/100);

            float neededGrade = ((weight*wantedGradeFloat+weight*currentGradeFloat-1*currentGradeFloat)/weight)*100;
                TextView resultantView = (TextView) findViewById(R.id.gradeOutput);
                resultantView.setText("You need a " + String.format("%.2f", neededGrade) +
                        "% in order to get a " + Float.toString(wantedGradeFloat*100) + "% in the class. Good Luck!" );
            }
        });

    }


}
