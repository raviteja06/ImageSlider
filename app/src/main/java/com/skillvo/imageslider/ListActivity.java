package com.skillvo.imageslider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    SliderApplication sliderApplication;
    ListView listView;
    TextView dataNotAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = (ListView) findViewById(R.id.listView);
        dataNotAvailable = (TextView) findViewById(R.id.data_not_available);
        sliderApplication = SliderApplication.getSliderApplication();
        loadJSON();
    }

    /**
     * loadJson will make the GET call to server to retrive the data
     * starts with a progress dialog. When the server call is done, progress dialog is closed.
     * JSON parse is also do here inside the onResponse
     */
    private void loadJSON() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Reading Values...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObjectRequest req = new JsonObjectRequest(Constants.JSONURL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        progressDialog.cancel();
                        // object to store List values
                        ArrayList<ListObject> listObjects = new ArrayList<>();
                        // check if the json has pagedList, Saves from JSON exeception
                        if (response.has("pagedList")) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("pagedList");
                                // loop thru the Array to get values
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String project_title = null;
                                    ArrayList<String> list_of_images = new ArrayList<>();
                                    // check if title exists
                                    if (jsonObject.has("title")) {
                                        project_title = jsonObject.getString("title");
                                    }
                                    // check if portfolio exists
                                    if (jsonObject.has("portfolio")) {
                                        JSONArray portfolioArray = jsonObject.getJSONArray("portfolio");
                                        for (int j = 0; j < portfolioArray.length(); j++) {
                                            JSONObject portfolioObject = portfolioArray.getJSONObject(j);
                                            if (portfolioObject.has("url")) {
                                                System.out.println(portfolioObject.getString("url"));
                                                // save all the images into a array
                                                list_of_images.add(portfolioObject.getString("url"));
                                            }
                                        }
                                    }
                                    // add list object to array list
                                    listObjects.add(new ListObject(project_title, list_of_images));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        // update the list view with values from server
                        updateListView(listObjects);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // if the call fails show data not available
                dataNotAvailable.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                progressDialog.cancel();
                error.printStackTrace();
            }
        });
        sliderApplication.addToRequestQueue(req);
    }

    /**
     *create the list view adapter and set the adapter to list view
     * @param listObjects ArrayList retrieved from server
     */
    private void updateListView(ArrayList<ListObject> listObjects) {
        ListViewAdapter listViewAdapter = new ListViewAdapter(this, listObjects);
        listView.setAdapter(listViewAdapter);
    }

    /**
     * click function if user clicks on view in list it will take them to new Activity
     * @param listObject
     */
    public void click(ListObject listObject) {
        Intent intent = new Intent(ListActivity.this, SliderActivity.class);
        intent.putExtra("title", listObject.getProjectName());
        intent.putExtra("images", listObject.getImages());
        startActivity(intent);
    }
}