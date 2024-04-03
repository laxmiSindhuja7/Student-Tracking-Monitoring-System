package com.example.schoolapp;

import java.util.ArrayList;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.text.InputFilter.LengthFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class CustomAdapter extends ArrayAdapter<DataModel> {
	 
    private ArrayList<DataModel> dataSet;
    Context mContext;
 
    // View lookup cache
    private static class ViewHolder {
    	TextView title;
    	TextView body;
    	
    }
 
    public CustomAdapter(ArrayList<DataModel> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext=context;
 
    }
 
    private int lastPosition = -1;
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
       final ViewHolder viewHolder; // view lookup cache stored in tag
 
        final View result;
 
        if (convertView == null) {
 
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title1);
            viewHolder.body = (TextView) convertView.findViewById(R.id.body1);
        
            result=convertView;
 
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
 
      
        lastPosition = position;
 
        viewHolder.title.setText(dataModel.getTitle1());
        viewHolder.body.setText(dataModel.getBody());
       
        // Return the completed view to render on screen
        return convertView;
    }
}
