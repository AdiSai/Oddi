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

import java.util.ArrayList;
import java.util.List;package muralimohan.spreadsheetbuddy.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import muralimohan.spreadsheetbuddy.R;

/**
 * Created by Murali on 7/8/2017.
 */

public class TimeLineAdapter extends BaseAdapter {
    private Context mContext;
    private final List<String> varsList;
    private final List<String> dataList;

    public TimeLineAdapter(Context c, List<String> varsList, List<String> dataList) {
        mContext = c;
        this.varsList = varsList;
        this.dataList = dataList;
    }

    public int getCount() {
        return varsList.size();
    }

    @Override
    public String getItem(int p) {
        return varsList.get(p);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(int index, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        final int position = index;//todo fix workaround
        //View list;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //list = new View(mContext);
            convertView = inflater.inflate(R.layout.data_display_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.editText = (EditText) convertView.findViewById(R.id.valueText);
            viewHolder.editText.setTag(index);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.variableText);
            viewHolder.id = index;
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(varsList.get(index));
        viewHolder.editText.setText(dataList.get(index));

        //checks for updates in the text in order to keep them changed in the ListView
        viewHolder.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){

                    EditText editText = (EditText) v.findViewById(R.id.valueText);
                    dataList.set(position, editText.getText().toString());
                }
            }
        });


        return convertView;
    }

    public String getDataPoint(int p){
        return dataList.get(p);
    }

    public int getIndexOfDataListMember(String member) throws IllegalArgumentException {
        String lowercaseMember = member.toLowerCase();
        List<String> matchingData = new ArrayList<String>();
        for(String dataPoint : dataList){
            if (dataPoint.toLowerCase().startsWith(lowercaseMember)) matchingData.add(dataPoint);
        }

        int possibleMatches = matchingData.size();
        if (possibleMatches == 0) throw new IllegalArgumentException("No matches");
        if (possibleMatches == 1){
            String match = matchingData.get(0);
            int index = dataList.indexOf(match);
            return index;
        }
        else throw new IllegalArgumentException("Too many matches");


    }

    class ViewHolder{
        EditText editText;
        TextView textView;
        int id;
    }
}

