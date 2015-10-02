package com.skillvo.imageslider;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SliderActivity extends FragmentActivity {
    String title = "Skillvo";
    ArrayList<String> images = new ArrayList<>();
    ArrayList<ImageObject> imageObjects = new ArrayList<>();
    HorizontalListView horizontalListView;
    ImageButton rotateLeft;
    ImageButton rotateRight;
    RecyclerViewPager mRecyclerView;
    ThumbnailListView thumbnailListView;
    LayoutAdapter layoutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        horizontalListView = (HorizontalListView) findViewById(R.id.hlvCustomList);
        rotateLeft = (ImageButton) findViewById(R.id.rotateLeft);
        rotateRight = (ImageButton) findViewById(R.id.rotateRight);
        mRecyclerView = (RecyclerViewPager) findViewById(R.id.list);
        ;

        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            title = bundle.getString("title", "skillvo");
            images = bundle.getStringArrayList("images");
        }
        for (String image : images) {
            imageObjects.add(new ImageObject(image, 0));
        }
        initListView();
        initViewPager();

        rotateLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thumbnailListView != null) {
                    thumbnailListView.setRotate(mRecyclerView.getCurrentPosition(), true);
                }
                if(layoutAdapter != null) {
                    layoutAdapter.setRotate(mRecyclerView.getCurrentPosition(), true);
                }
                if (horizontalListView.getChildAt(mRecyclerView.getCurrentPosition()) != null)
                    horizontalListView.getChildAt(mRecyclerView.getCurrentPosition()).setBackgroundColor(Color.parseColor("#FFC107"));
            }
        });

        rotateRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thumbnailListView != null) {
                    thumbnailListView.setRotate(mRecyclerView.getCurrentPosition(), false);
                }
                if(layoutAdapter != null) {
                    layoutAdapter.setRotate(mRecyclerView.getCurrentPosition(), false);
                }
                if (horizontalListView.getChildAt(mRecyclerView.getCurrentPosition()) != null)
                    horizontalListView.getChildAt(mRecyclerView.getCurrentPosition()).setBackgroundColor(Color.parseColor("#FFC107"));
            }
        });
    }

    @SuppressWarnings("deprecation")
    private void initViewPager() {
        layoutAdapter = new LayoutAdapter(this, imageObjects);
        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layout);//setLayoutManager
        mRecyclerView.setAdapter(layoutAdapter);

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
                // do something
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                if (horizontalListView != null) {
                    horizontalListView.scrollTo(mRecyclerView.getCurrentPosition());
                    for (int f = 0; f < imageObjects.size(); f++) {
                        if (f == mRecyclerView.getCurrentPosition()) {
                            if (horizontalListView.getChildAt(mRecyclerView.getCurrentPosition()) != null)
                                horizontalListView.getChildAt(mRecyclerView.getCurrentPosition()).setBackgroundColor(Color.parseColor("#FFC107"));
                        } else {
                            if (horizontalListView.getChildAt(f) != null)
                                horizontalListView.getChildAt(f).setBackgroundColor(Color.parseColor("#FFFFFF"));
                        }
                    }
                }

                int childCount = mRecyclerView.getChildCount();
                int width = mRecyclerView.getChildAt(0).getWidth();
                int padding = (mRecyclerView.getWidth() - width) / 2;

                for (int j = 0; j < childCount; j++) {
                    View v = recyclerView.getChildAt(j);
                    float rate = 0;
                    if (v.getLeft() <= padding) {
                        if (v.getLeft() >= padding - v.getWidth()) {
                            rate = (padding - v.getLeft()) * 1f / v.getWidth();
                        } else {
                            rate = 1;
                        }
                        v.setScaleY(1 - rate * 0.1f);
                    } else {
                        if (v.getLeft() <= recyclerView.getWidth() - padding) {
                            rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
                        }
                        v.setScaleY(0.9f + rate * 0.1f);
                    }
                }
            }
        });

        mRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (mRecyclerView.getChildCount() < 3) {
                    if (mRecyclerView.getChildAt(1) != null) {
                        View v1 = mRecyclerView.getChildAt(1);
                        v1.setScaleY(0.9f);
                    }
                } else {
                    if (mRecyclerView.getChildAt(0) != null) {
                        View v0 = mRecyclerView.getChildAt(0);
                        v0.setScaleY(0.9f);
                    }
                    if (mRecyclerView.getChildAt(2) != null) {
                        View v2 = mRecyclerView.getChildAt(2);
                        v2.setScaleY(0.9f);
                    }
                }

            }
        });
    }

    private void initListView() {
        thumbnailListView = new ThumbnailListView(this, imageObjects);
        horizontalListView.setAdapter(thumbnailListView);
        horizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mRecyclerView != null)
                    mRecyclerView.scrollToPosition(position);

                for (int i = 0; i < imageObjects.size(); i++) {
                    if (i == position) {
                        if (horizontalListView.getChildAt(position) != null)
                            horizontalListView.getChildAt(position).setBackgroundColor(Color.parseColor("#FFC107"));
                    } else {
                        if (horizontalListView.getChildAt(i) != null)
                            horizontalListView.getChildAt(i).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }
                }
            }
        });
    }
}
