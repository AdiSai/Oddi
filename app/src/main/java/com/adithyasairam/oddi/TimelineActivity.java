package com.adithyasairam.oddi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.adithyasairam.oddi.pojos.Assignment;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

public class TimelineActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    Map<String, Assignment> assignmentMap =  null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            { Intent intent = new Intent (TimelineActivity.this, CreateActivity.class);
                startActivity(intent);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        try {
            FileInputStream fis = new FileInputStream("assignment.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            assignmentMap = (Map<String, Assignment>) ois.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //todo load classes and assignments from file

    }
    public void openFGC(View view)
    {
        Intent intent = new Intent(TimelineActivity.this, FinalGradeActivity.class);
        startActivity(intent);
    }
}
