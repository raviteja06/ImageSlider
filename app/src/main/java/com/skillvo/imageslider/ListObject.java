package com.skillvo.imageslider;

import java.util.ArrayList;

/**
 * Created by Raviteja on 10/1/2015. Image Slider
 */
public class ListObject {
    private String projectName;
    private ArrayList<String> images;

    public ListObject(String projectName, ArrayList<String> images) {
        this.projectName = projectName;
        this.images = images;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }
}
