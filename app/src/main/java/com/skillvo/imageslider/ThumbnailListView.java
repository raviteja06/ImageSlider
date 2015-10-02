package com.skillvo.imageslider;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Raviteja on 10/2/2015. Image Slider
 */
public class ThumbnailListView extends ArrayAdapter<ImageObject> {
    Context context;
    ArrayList<ImageObject> imageObjects;
    private static SparseBooleanArray selectedItems;
    boolean set = false;
    int selectPosition = 0;
    /**
     * Constructor to initialize the values
     *
     * @param context      Activity context
     * @param imageObjects List of objects
     */
    public ThumbnailListView(Context context, ArrayList<ImageObject> imageObjects) {
        super(context, R.layout.list_images, imageObjects);
        this.selectedItems = new SparseBooleanArray();
        selectedItems.put(0, true);
        this.context = context;
        this.imageObjects = imageObjects;
        set = false;
    }

    // return total count for views
    @Override
    public int getCount() {
        return imageObjects.size();
    }

    /**
     * return list object at specific position
     *
     * @param arg0 position
     * @return ListObject
     */
    @Override
    public ImageObject getItem(int arg0) {
        return imageObjects.get(arg0);
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

    public View getView(final int position, View convertView, ViewGroup parent) {

        // get the object based on position
        final ImageObject imageObject = getItem(position);
        // Set inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // set view
        final View rowView = inflater.inflate(R.layout.list_images, parent, false);
        rowView.setId(position);

        // UI elements
        final ImageView imageView = (ImageView) rowView.findViewById(R.id.thumbnail);
        final RelativeLayout background = (RelativeLayout) rowView.findViewById(R.id.background);
        if (position == selectPosition && !set) {
            set = true;
            rowView.setBackgroundColor(Color.parseColor("#FFC107"));
        }

        displayImage(imageView, imageObject);
        return rowView;
    }

    private void displayImage(ImageView imageView, ImageObject imageObject) {
        Picasso.with(context)
                .load(imageObject.getImage().replace(".jpg", "-thumb.jpg"))
                .rotate(imageObject.getRotate())
                .resize(100, 100)
                .into(imageView);
    }

    public void setRotate(int position, boolean isLeft) {
        ImageObject imageObject = imageObjects.get(position);
        float rotate = imageObject.getRotate();
        if (isLeft) {
            if (rotate == 0)
                imageObject.setRotate(360 - 90);
            else
                imageObject.setRotate(rotate - 90);
        } else {
            if (rotate == 360)
                imageObject.setRotate(90);
            else
                imageObject.setRotate(rotate + 90);
        }
        imageObjects.set(position, imageObject);
        notifyDataSetChanged();
        selectPosition = position;
        set = false;
    }
}

