package com.adithyasairam.oddi.Adapters;

/**
 * Created by Murali on 9/24/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.adithyasairam.oddi.R;
import com.adithyasairam.oddi.pojos.Assignment;

import java.util.List;

/**
 * Created by Murali on 7/8/2017.
 */

public class TimeLineAdapter extends BaseAdapter {
    private Context mContext;
    private List<Assignment> assignmentList;

    public TimeLineAdapter(Context c, List<Assignment> assignments) {
        mContext = c;
        assignmentList = assignments;
    }

    public int getCount() {
        return assignmentList.size();
    }

    @Override
    public Assignment getItem(int p) {
        return assignmentList.get(p);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(int index, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        final int position = index;//todo fix workaround
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_timeline, null);
            viewHolder = new ViewHolder();
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TextView title = (TextView) convertView.findViewById(R.id.ClassAndType);
        title.setText(assignmentList.get(index).getAssignmentClass() + " - "+
                assignmentList.get(index).getAssignmentType());
        TextView name = (TextView) convertView.findViewById(R.id.Name);
        name.setText(assignmentList.get(index).getAssignmentName());
        TextView date = (TextView) convertView.findViewById(R.id.Date);
        date.setText(assignmentList.get(index).getDueDate());
        return convertView;
    }

    class ViewHolder{
        EditText editText;
        TextView textView;
        int id;
    }
}

