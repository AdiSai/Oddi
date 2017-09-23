package com.adithyasairam.oddi.Adapters;

import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adithyasairam.oddi.R;
import com.adithyasairam.oddi.pojos.Assignment;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Murali on 9/23/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter {

    private ArrayList<Assignment> assignmentsList = new ArrayList();

    //1
    public static class AssignmentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //2
        private TextView classTV;
        private TextView nameTV;
        private TextView dateTV;
        private LinearLayout timelineItem;

        //3
        private static final String PHOTO_KEY = "PHOTO";

        //4
        public AssignmentHolder(View v) {
            super(v);
            classTV = (TextView) v.findViewById(R.id.classTypeTV);
            nameTV = (TextView) v.findViewById(R.id.nameText);
            dateTV = (TextView) v.findViewById(R.id.dueDate);
            timelineItem = (LinearLayout) v.findViewById(R.id.itemLayout);

            v.setOnClickListener(this);
        }

        public void bindAssignment(Assignment assignment) {
           classTV.setText(assignment.getAssignmentName() + " - " + assignment.getAssignmentType());
           nameTV.setText(assignment.getAssignmentName());
           dateTV.setText(assignment.getDueDate());
           switch(assignment.getAssignmentType()){
               case "Homework":
                   timelineItem.setBackgroundResource(R.color.colorHomework);
                   break;
               case "Project":
                   timelineItem.setBackgroundResource(R.color.colorProject);
                   break;
               case "Exam":
                   timelineItem.setBackgroundResource(R.color.colorExam);
                   break;
               default:
                   break;
           }
        }

        //5
        @Override
        public void onClick(View v) {
            Log.d("RecyclerView", "CLICK!");
            //does nothing
        }
    }


    public RecyclerAdapter(ArrayList<Assignment> assignments){
        this.assignmentsList = assignments;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timelinelayout, parent, false);
        return new AssignmentHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Assignment assignment= assignmentsList.get(position);
        AssignmentHolder h = (AssignmentHolder) holder;
        h.bindAssignment(assignment);
    }

    @Override
    public int getItemCount() {
        return this.assignmentsList.size();
    }
}
