package com.example.jsakhare.androiddownloadmanager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TextFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TextFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public  class TextFragment extends Fragment{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private String url;
    EditText link;
    Button go;
    ProgressBar proBar;
    TextView responseText;
    ResponseHandler tHandler;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    @Override
    public void onStart() {
        super.onStart();

    }


    public TextFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
       // setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_text, container, false);
        responseText = (TextView)rootView.findViewById(R.id.response_text);
        go = (Button)rootView.findViewById(R.id.go);
        link = (EditText)rootView.findViewById(R.id.url);
        proBar = (ProgressBar)rootView.findViewById(R.id.progressBar1);
        // Instantiate the RequestQueue.

         //tHandler = new TextHandler(this);
        go.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                url = link.getText().toString();
                if(!url.isEmpty() && url != null){
                    responseText.setText("");
                    proBar.setVisibility(View.VISIBLE);

                    RequestQueue queue = VolleySingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue();
                    // Request a string response from the provided URL.
                    StringRequest request = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Display the first 500 characters of the response string.
                                    proBar.setVisibility(View.GONE);
                                    responseText.setText("Response is: "+ response);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            responseText.setText("Failed!");
                            proBar.setVisibility(View.GONE);
                        }
                    });


                    if(queue.getCache().get(url)!=null){

                        //response exists
                        String cachedResponse = new String(queue.getCache().get(url).data);
                        responseText.setText("From Cache: " + cachedResponse);
                        proBar.setVisibility(View.GONE);
                    }else{
                        //no response
                        queue.add(request);
                    }
                }
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onStop() {
        super.onStop();
        VolleySingleton.getInstance(getActivity()).getRequestQueue().cancelAll(this);
    }
}
