package com.skillvo.imageslider;

/**
 * Created by Raviteja on 10/2/2015. Image Slider
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ThumbnailRecyclerView extends RecyclerView.Adapter {
    Context context;
    ArrayList<ImageObject> imageObjects;
    private static SparseBooleanArray selectedItems;
    private static RelativeLayout lastBackground = null;
    private static int lastCheckedPos = 0;
    boolean set = false;
    int selectedPosition = 0;

    public ThumbnailRecyclerView(Context context, ArrayList<ImageObject> imageObjects) {
        this.context = context;
        this.imageObjects = imageObjects;
        selectedItems = new SparseBooleanArray();
        set = false;
        selectedPosition = 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderMain(LayoutInflater.from(context).inflate(R.layout.list_images, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        final ViewHolderMain viewHolderMain = (ViewHolderMain) holder;
        viewHolderMain.background.setBackgroundColor(Color.parseColor("#FFFFFF"));
        viewHolderMain.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SliderActivity) context).click(position);
                if (lastBackground != null) {
                    selectedItems.delete(lastCheckedPos);
                    lastBackground.setBackgroundColor(Color.parseColor("#FFFFFF"));
                } else {
                    lastBackground = null;
                }
                if (selectedItems.get(position, false)) {
                    selectedItems.delete(position);
                    viewHolderMain.background.setBackgroundColor(Color.parseColor("#FFFFFF"));
                } else {
                    selectedItems.put(position, true);
                    viewHolderMain.background.setBackgroundColor(Color.parseColor("#FFC107"));
                    lastBackground = viewHolderMain.background;
                    lastCheckedPos = position;
                }
            }
        });
        if (position == selectedPosition && !set) {
            set = true;
            selectedItems.put(position, true);
            viewHolderMain.background.setBackgroundColor(Color.parseColor("#FFC107"));
            lastBackground = viewHolderMain.background;
            lastCheckedPos = position;
        }
        if (imageObjects.get(position).isSelected()) {
            selectedItems.put(position, true);
            viewHolderMain.background.setBackgroundColor(Color.parseColor("#FFC107"));
            lastBackground = viewHolderMain.background;
            lastCheckedPos = position;
        }
        displayImage(viewHolderMain.imageView, imageObjects.get(position));
    }

    @Override
    public int getItemCount() {
        return imageObjects.size();
    }

    private class ViewHolderMain extends RecyclerView.ViewHolder {
        ImageView imageView;
        RelativeLayout background;

        public ViewHolderMain(View inflate) {
            super(inflate);
            imageView = (ImageView) inflate.findViewById(R.id.thumbnail);
            background = (RelativeLayout) inflate.findViewById(R.id.background);
        }
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
        selectedPosition = position;
        set = false;
    }
}

