package com.example.btl2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl2.models.NotiModel;
import com.example.btl2.models.Product;

import java.util.List;

public class NotiModelAdapter extends RecyclerView.Adapter<NotiModelAdapter.ViewHolder> {
    Context context;
    List<NotiModel> list;

    public NotiModelAdapter(Context context, List<NotiModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NotiModelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.noti_vertical_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotiModelAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageResource(list.get(position).getImage());
        holder.headNoti.setText(list.get(position).getHeadNoti());
        holder.noti.setText(list.get(position).getNoti());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView headNoti, noti;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageNoti);
            headNoti = (TextView) itemView.findViewById(R.id.textNoti1);
            noti = (TextView) itemView.findViewById(R.id.textNoti2);
        }
    }
}
