package com.example.btl2.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.btl2.R;

import java.util.List;

public class ProductImageAdapter extends BaseAdapter {
    private Context context;
    private List<Uri> imagePaths;
    private LayoutInflater inflater;

    public ProductImageAdapter(Context context, List<Uri> imagePaths) {
        this.context = context;
        this.imagePaths = imagePaths;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imagePaths.size();
    }

    @Override
    public Object getItem(int position) {
        return imagePaths.get(position);
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
        Uri imagePath = imagePaths.get(position);
        imageView.setImageURI(imagePath);
        return convertView;
    }
}
