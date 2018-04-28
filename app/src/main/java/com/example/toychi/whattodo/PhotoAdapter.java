package com.example.toychi.whattodo;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class PhotoAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> photoUri;

    public PhotoAdapter(Context appContext, ArrayList<String> item){
        this.mContext = appContext;
        this.photoUri = new ArrayList<>(item);
    }

    @Override
    public int getCount() {
        return photoUri.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if (view == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) view;
        }
        Uri myUri = Uri.parse(photoUri.get(i));
        imageView.setImageURI(myUri);
        return imageView;
    }
}
