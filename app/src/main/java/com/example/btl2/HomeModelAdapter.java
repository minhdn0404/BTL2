package com.example.btl2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeModelAdapter extends RecyclerView.Adapter<HomeModelAdapter.ViewHolder> {

    Context context;
    List<HomeModel> list;

    public HomeModelAdapter(Context context, List<HomeModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HomeModelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_vertical_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeModelAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageResource(list.get(position).getImage());
        holder.name.setText(list.get(position).getName());
        holder.startPrice.setText(String.valueOf(list.get(position).getStartPrice()));
        holder.stepPrice.setText(String.valueOf(list.get(position).getStepPrice()));
        holder.time.setText(list.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name, time, startPrice, stepPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_model);
            name = itemView.findViewById(R.id.name_model);
            time = itemView.findViewById(R.id.time_model);
            startPrice = itemView.findViewById(R.id.startPrice_model);
            stepPrice = itemView.findViewById(R.id.stepPrice_model);
        }
    }
}
