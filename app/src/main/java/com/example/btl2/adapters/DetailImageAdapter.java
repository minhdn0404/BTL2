package com.example.btl2.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.btl2.R;

import java.util.List;

public class DetailImageAdapter extends BaseAdapter {
    private Context context;
    private List<Bitmap> imageBitmaps;
    private LayoutInflater inflater;
    public DetailImageAdapter(Context context, List<Bitmap> imageBitmaps) {
        this.context = context;
        this.imageBitmaps = imageBitmaps;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return imageBitmaps.size();
    }

    @Override
    public Object getItem(int position) {
        return imageBitmaps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.product_image_item, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView);
        Bitmap bitmap = imageBitmaps.get(position);
        imageView.setImageBitmap(bitmap);
        return convertView;
    }
}
