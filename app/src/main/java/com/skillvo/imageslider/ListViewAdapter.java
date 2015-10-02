package com.skillvo.imageslider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Raviteja on 10/1/2015. Image Slider
 */
public class ListViewAdapter extends ArrayAdapter<ListObject> {
    Context context;
    ArrayList<ListObject> listObjects;

    /**
     * Constructor to initialize the values
     *
     * @param context     Activity context
     * @param listObjects List of objects
     */
    public ListViewAdapter(Context context, ArrayList<ListObject> listObjects) {
        super(context, R.layout.list_layout, listObjects);
        this.context = context;
        this.listObjects = listObjects;
    }

    // return total count for views
    @Override
    public int getCount() {
        return listObjects.size();
    }

    /**
     * return list object at specific position
     *
     * @param arg0 position
     * @return ListObject
     */
    @Override
    public ListObject getItem(int arg0) {
        return listObjects.get(arg0);
    }

    /**
     * get current position
     *
     * @param arg0 position
     * @return position as long
     */
    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // get the object based on position
        final ListObject listObject = getItem(position);
        // Set inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // set view
        final View rowView = inflater.inflate(R.layout.list_layout, parent, false);
        rowView.setId(position);

        // UI elements
        final TextView project_name = (TextView) rowView.findViewById(R.id.project_name);
        final TextView number_of_images = (TextView) rowView.findViewById(R.id.number_of_photos);
        final RadioButton radioButton = (RadioButton) rowView.findViewById(R.id.radio_button);

        // set values
        project_name.setText(listObject.getProjectName());
        number_of_images.setText(String.valueOf(listObject.getImages().size()) + " Photos");

        // click listener for row.
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton.setChecked(true);
                Toast.makeText(context, listObject.getProjectName() + " is selected", Toast.LENGTH_SHORT).show();
                ((ListActivity) context).click(listObject);
                radioButton.setChecked(false);
            }
        });
        return rowView;
    }
}

