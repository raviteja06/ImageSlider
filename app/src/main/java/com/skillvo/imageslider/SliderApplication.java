package com.skillvo.imageslider;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Raviteja on 10/1/2015. Image Slider
 */
public class SliderApplication extends Application {
    private static SliderApplication sliderApplication;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        sliderApplication = this;
    }

    // volley request queue
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    // get application context
    public static SliderApplication getSliderApplication() {
        return sliderApplication;
    }

    // add to volley queue with tag
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? "Chat AI" : tag);
        getRequestQueue().add(req);
    }

    // add to volley queue
    public <T> void addToRequestQueue(Request<T> req) {
        req.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        req.setTag("Image Slider");
        getRequestQueue().add(req);
    }

    // cancel all pending requests
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
