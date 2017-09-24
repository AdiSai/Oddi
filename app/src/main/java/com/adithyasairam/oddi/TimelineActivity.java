package com.adithyasairam.oddi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.adithyasairam.oddi.pojos.Assignment;
import com.adithyasairam.oddi.pojos.Class;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

public class TimelineActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    Map<String, Assignment> assignmentMap = null;
    Map<String, Class> classMap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TimelineActivity.this, CreateActivity.class);
                startActivity(intent);
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        try {
            File file = new File(OddiApp.getInternalDataDir().getAbsolutePath(), "assignment.ser");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            assignmentMap = (Map<String, Assignment>) ois.readObject();
            file = new File(OddiApp.getInternalDataDir().getAbsolutePath(), "class.ser");
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            classMap = (Map<String, Class>) ois.readObject();
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.timeLine:
                break;
            case R.id.classes:
                Intent intent = new Intent(TimelineActivity.this, ClassCreatorActivity.class);
                startActivity(intent);
                break;
            case R.id.FGC:
                Intent intent1 = new Intent (TimelineActivity.this, FinalGradeActivity.class);
                startActivity(intent1);
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);

    }



        //todo load classes and assignments from file
}