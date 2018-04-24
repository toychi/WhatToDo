package com.example.toychi.whattodo;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

public class TaskImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> ImageList;
    //private List<Bitmap> ImageList;

    public TaskImageAdapter(Context context, List<String> list) {
    //public TaskImageAdapter(Context context, List<Bitmap> list) {
        mContext = context;
        ImageList = list;
    }

    public int getCount() {
        return 20;
    }

    public Object getItem(int position) {
        return ImageList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }



    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 200));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(4, 4, 4, 4);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(android.R.drawable.ic_menu_delete);

        return imageView;
    }
}

