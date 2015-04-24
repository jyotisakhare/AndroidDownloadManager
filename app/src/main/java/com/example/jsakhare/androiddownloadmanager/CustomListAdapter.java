package com.example.jsakhare.androiddownloadmanager;

/**
 * Created by jsakhare on 11/04/15.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class CustomListAdapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<String> imageList;
    ImageLoader imageLoader ;

    public CustomListAdapter(Activity activity, List<String> imageList) {
        this.activity = activity;
        this.imageList = imageList;
        imageLoader = VolleySingleton.getInstance(activity).getImageLoader();
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public String getItem(int location) {
        return imageList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = VolleySingleton.getInstance(activity).getImageLoader();

        //ProgressBar progressBar = (ProgressBar) convertView.findViewById( R.id.progressBar2 );
        //progressBar.setVisibility(View.VISIBLE);
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);

        String imageUrl = imageList.get(position);

        thumbNail.setImageUrl(imageUrl, imageLoader);
        //progressBar.setVisibility(View.GONE);
        return convertView;
    }
}