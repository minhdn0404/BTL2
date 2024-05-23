package com.example.btl2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl2.models.Product;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    Context context;
    List<Product> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public ProductListAdapter(Context context, List<Product> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_vertical_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageBitmap(list.get(position).getImage().get(0));
        holder.name.setText(list.get(position).getName());
        holder.startPrice.setText(String.valueOf(list.get(position).getStartPrice()));
        holder.stepPrice.setText(String.valueOf(list.get(position).getStepPrice()));
        String startTime = list.get(position).getAuctionStartTime();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm  dd-MM-yyyy");
            LocalDateTime dateTime = LocalDateTime.parse(startTime, formatter);
            if (dateTime.isBefore(LocalDateTime.now())) {
                holder.time.setText("Đang diễn ra");
            } else {
                holder.time.setText(list.get(position).getAuctionStartTime());
            }
        }
        holder.bind(list.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

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

        public void bind(final Product product, final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(product);
                }
            });
        }
    }
}
