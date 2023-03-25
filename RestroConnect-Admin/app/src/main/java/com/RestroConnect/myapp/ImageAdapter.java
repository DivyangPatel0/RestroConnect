package com.RestroConnect.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.RestroConnect.app.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    public List<FirebaseImage> imageList;
    private Context context;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView titleTextView, count_text;
        private Button add_btn, minus_btn;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            titleTextView = itemView.findViewById(R.id.text_view);
            count_text = itemView.findViewById(R.id.menu_counter);
            add_btn = itemView.findViewById(R.id.menu_add_btn);
            minus_btn = itemView.findViewById(R.id.menu_minus_btn);
        }
    }

    public ImageAdapter(List<FirebaseImage> imageList) {
        this.imageList = imageList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_image, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Image image = imageList.get(position);

        Glide.with(holder.imageView.getContext()).load(image.getImageUrl()).into(holder.imageView);
        holder.titleTextView.setText(image.getTitle());

        holder.add_btn.setOnClickListener(v -> {
            int count = image.getCount() + 1;
            image.setCount(count);
            holder.count_text.setText(String.valueOf(count));
        });

        holder.minus_btn.setOnClickListener(v -> {
            if (image.getCount() != 0) {
                int count = image.getCount() - 1;
                image.setCount(count);
                holder.count_text.setText(String.valueOf(count));
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}