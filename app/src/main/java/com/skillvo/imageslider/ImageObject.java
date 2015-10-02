package com.skillvo.imageslider;

/**
 * Created by Raviteja on 10/2/2015. Image Slider
 */
public class ImageObject {
    private String image;
    private float rotate;

    public ImageObject(String image, float rotate) {
        this.image = image;
        this.rotate = rotate;
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
