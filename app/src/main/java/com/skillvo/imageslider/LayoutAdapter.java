package com.skillvo.imageslider;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Raviteja on 10/2/2015. Image Slider
 */
public class LayoutAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<ImageObject> imageObjects;

    public LayoutAdapter(Context context, ArrayList<ImageObject> imageObjects) {
        this.context = context;
        this.imageObjects = imageObjects;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderMain(LayoutInflater.from(context).inflate(R.layout.image_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolderMain viewHolderMain = (ViewHolderMain) holder;
        displayImage(viewHolderMain.imageView, imageObjects.get(position));
    }

    @Override
    public int getItemCount() {
        return imageObjects.size();
    }

    private class ViewHolderMain extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolderMain(View inflate) {
            super(inflate);
            imageView = (ImageView) inflate.findViewById(R.id.imageView);
        }
    }

    private void displayImage(ImageView imageView, ImageObject imageObject) {
        Picasso.with(context)
                .load(imageObject.getImage())
                .rotate(imageObject.getRotate())
                .into(imageView);
    }

    public void setRotate(int position, boolean isLeft) {
//        ImageObject imageObject = imageObjects.get(position);
//        float rotate = imageObject.getRotate();
//        if (isLeft) {
//            if (rotate == 0)
//                imageObject.setRotate(360 - 90);
//            else
//                imageObject.setRotate(rotate - 90);
//        } else {
//            if (rotate == 360)
//                imageObject.setRotate(90);
//            else
//                imageObject.setRotate(rotate + 90);
//        }
//        imageObjects.set(position, imageObject);
        notifyDataSetChanged();
    }
}
