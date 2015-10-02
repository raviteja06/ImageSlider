package com.skillvo.imageslider;

/**
 * Created by Raviteja on 10/2/2015. Image Slider
 */
public class ImageObject {
    private String image;
    private float rotate;
    private boolean isSelected;

    public ImageObject(String image, float rotate, boolean isSelected) {
        this.image = image;
        this.rotate = rotate;
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getRotate() {
        return rotate;
    }

    public void setRotate(float rotate) {
        this.rotate = rotate;
    }
}
