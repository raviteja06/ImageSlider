package com.skillvo.imageslider;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.ArrayList;

public class SliderActivity extends FragmentActivity {
    String title = "Skillvo";
    ArrayList<String> images = new ArrayList<>();
    ArrayList<ImageObject> imageObjects = new ArrayList<>();
    ImageButton rotateLeft;
    ImageButton rotateRight;
    RecyclerViewPager mRecyclerView;
    ThumbnailRecyclerView thumbnailRecyclerView;
    LayoutAdapter layoutAdapter;
    RecyclerView recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        rotateLeft = (ImageButton) findViewById(R.id.rotateLeft);
        rotateRight = (ImageButton) findViewById(R.id.rotateRight);
        mRecyclerView = (RecyclerViewPager) findViewById(R.id.list);
        recycler_view = (RecyclerView) findViewById(R.id.recyclerView);

        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            title = bundle.getString("title", "skillvo");
            images = bundle.getStringArrayList("images");
        }
        for (String image : images) {
            imageObjects.add(new ImageObject(image, 0));
        }
        initListView();
        initRecyclerView();

        rotateLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thumbnailRecyclerView != null) {
                    thumbnailRecyclerView.setRotate(mRecyclerView.getCurrentPosition(), true);
                }
                if (layoutAdapter != null) {
                    layoutAdapter.setRotate(mRecyclerView.getCurrentPosition(), true);
                }
            }
        });

        rotateRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thumbnailRecyclerView != null) {
                    thumbnailRecyclerView.setRotate(mRecyclerView.getCurrentPosition(), false);
                }
                if (layoutAdapter != null) {
                    layoutAdapter.setRotate(mRecyclerView.getCurrentPosition(), false);
                }
//                if (horizontalListView.getChildAt(mRecyclerView.getCurrentPosition()) != null)
//                    horizontalListView.getChildAt(mRecyclerView.getCurrentPosition()).setBackgroundColor(Color.parseColor("#FFC107"));
            }
        });
    }

    @SuppressWarnings("deprecation")
    private void initRecyclerView() {
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
                int position = mRecyclerView.getCurrentPosition();
                System.out.println(position);
                if (recycler_view != null) {
                    recycler_view.smoothScrollToPosition(position);
                    thumbnailRecyclerView.setSelfSelectedPosition(position);
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
        thumbnailRecyclerView = new ThumbnailRecyclerView(this, imageObjects);
        recycler_view.removeAllViews();
        recycler_view.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setAdapter(thumbnailRecyclerView);
    }

    public void click(int position) {
        if (mRecyclerView != null)
            mRecyclerView.scrollToPosition(position);
    }
}
