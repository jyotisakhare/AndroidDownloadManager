package com.example.jsakhare.androiddownloadmanager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ImageFragment extends Fragment {

    private static final String TAG = ImageFragment.class.getSimpleName();
    private static final String url = "http://api.androidhive.info/json/movies.json";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ListView listView;
    private List<String>  imageList = new ArrayList<String>() ; ;
   // private ProgressDialog prgDialog;
    private CustomListAdapter adapter;

    @Override
    public void onStart() {
        super.onStart();

    }


    public ImageFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        // setHasOptionsMenu(true);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image, container, false);
        //final ImageView imageView = (ImageView)rootView.findViewById(R.id.imageView);
        listView = (ListView) rootView.findViewById(R.id.image_list);
        adapter = new CustomListAdapter(getActivity(), imageList);
        listView.setAdapter(adapter);

        JsonArrayRequest imageReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        //hideProgressDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                String imageUrl = obj.getString("image");
                                imageList.add(imageUrl);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //hideProgressDialog();

            }
        });
        RequestQueue queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();
        queue.add(imageReq);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onStop() {
        super.onStop();
        VolleySingleton.getInstance(getActivity()).getRequestQueue().cancelAll(this);
    }
}
