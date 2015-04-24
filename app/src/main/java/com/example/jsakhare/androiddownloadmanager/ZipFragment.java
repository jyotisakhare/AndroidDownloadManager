package com.example.jsakhare.androiddownloadmanager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class ZipFragment extends Fragment implements ItemDataHandler<String>{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
   // private ProgressBar mProgress;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private String url;
    EditText link;
    Button go;
    TextView responseText;
    ResponseHandler zHandler;
    private ProgressDialog prgDialog;
    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;
    int id = 1;



    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    @Override
    public void onStart() {
        super.onStart();

    }


    public ZipFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        // setHasOptionsMenu(true);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_zip, container, false);
        responseText = (TextView)rootView.findViewById(R.id.response_text);
        go = (Button)rootView.findViewById(R.id.go);
        link = (EditText)rootView.findViewById(R.id.url);
        zHandler = new ZipHandler(this);
        //for  notification on drop down screen
        mNotifyManager = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(getActivity());
        mNotifyManager.notify(id, mBuilder.build());

        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setMessage(" Please wait ");
        prgDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        prgDialog.setIndeterminate(false);
        prgDialog.setCancelable(true);
        prgDialog.setCanceledOnTouchOutside(false);

        go.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                url = link.getText().toString();
                if (!url.isEmpty() && url != null) {
                    responseText.setText("");
                    prgDialog.show();
                    mBuilder.setContentTitle("Download")
                            .setContentText("Download in progress")
                            .setSmallIcon(R.drawable.ic_action_download_dark);
                    mBuilder.setProgress(100, 0, false);
                    RequestDataFromServer.getZipResponse(zHandler, url);
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
    public void setItem(String item) {
        prgDialog.dismiss();
        mBuilder.setContentText("Download complete");
        // Removes the progress bar
        mBuilder.setProgress(0, 0, false);
        mNotifyManager.notify(id, mBuilder.build());
        responseText.setText(item);
    }

    @Override
    public void onError(BaseResponse result) {
        prgDialog.dismiss();
        mBuilder.setContentText("Download Failed");
        // Removes the progress bar
        mBuilder.setProgress(0, 0, false);
        mNotifyManager.notify(id, mBuilder.build());
        responseText.setText("Download Failed");

    }

    @Override
    public void onProgressUpdate(String value) {
        Integer v = Integer.parseInt(value);
        mBuilder.setProgress(100, v, false);
        mNotifyManager.notify(id, mBuilder.build());
        prgDialog.setProgress(v);
    }
}
