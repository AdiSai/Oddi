package com.adithyasairam.oddi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
                float currentGradeFloat = (Float.parseFloat(currentGrade.getText().toString()))/100;
            EditText wantedGrade = (EditText)findViewById(R.id.goalClassGrade);
                float wantedGradeFloat = (Float.parseFloat(wantedGrade.getText().toString()))/100;
            EditText gradeWeight = (EditText)findViewById(R.id.weightGrade);
                float weight = (Float.parseFloat(gradeWeight.getText().toString())/100);
                if (currentGrade.getText() != null && wantedGrade.getText() != null && gradeWeight.getText() != null)
                {
                    float need = wantedGradeFloat-currentGradeFloat+(currentGradeFloat*weight);
                    float neededGrade = (need/weight)*100;

                    TextView resultantView = (TextView) findViewById(R.id.gradeOutput);

                    if (neededGrade>90)
                    {
                        resultantView.setText("You need a " + String.format("%.2f", neededGrade)+ "%. " + "Go study for your final, you're going to need to....");
                    }
                    else if (neededGrade>80)
                    {
                        resultantView.setText("You need a " + String.format("%.2f", neededGrade)+ "%. " + "Final should not be TOO bad for you");
                    }
                    else if (neededGrade>70)
                    {
                        resultantView.setText("You need a " + String.format("%.2f", neededGrade)+ "%. " + "So nice that you can relax after a semester of hard work now");
                    }
                    else if (neededGrade>60)
                    {
                        resultantView.setText("You need a " + String.format("%.2f", neededGrade)+ "%. " + "Great job the past semester");
                    }
                    else
                    {
                        resultantView.setText("You need a " + String.format("%.2f", neededGrade)+ "%. " + "Bubble in random answers..??");
                    }

                }
                else

                {

                    Snackbar.make(findViewById(R.id.FGC), "Please fill in all the fields!", Snackbar.LENGTH_SHORT);
                }
                wantedGradeFloat = 0;
                weight = 0;
                currentGradeFloat = 0;
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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.timeLine:
                Intent intent1 = new Intent (FinalGradeActivity.this, TimelineActivity.class);
                startActivity(intent1);
                break;
            case R.id.classes:
                Intent intent = new Intent(FinalGradeActivity.this, ClassCreatorActivity.class);
                startActivity(intent);
                break;
            case R.id.FGC:
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);

    }


}
