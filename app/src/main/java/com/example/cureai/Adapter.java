package com.example.cureai;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<String> descriptions;
    private LayoutInflater inflater;

    Adapter(Context context, List<String> descriptions, final List<String> terms) {
        this.descriptions = new ArrayList<>();
        for (String entry : descriptions) {
            boolean added = false;
            for (String term : terms) {
                if (entry.contains(term) && !added) {
                    added = true;
                    this.descriptions.add(entry);
                }
            }
        }
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String desc = descriptions.get(position);
//        String img = images.get(position);
//        Picasso.get().load(images.get(position)).placeholder(R.drawable.placeholder).error(R.drawable.placeholder)
//                //.resize(75,75)
//                .fit()
//                .centerCrop()
//                .into(holder.thumbnail);
        holder.desc.setText(desc);
//        Picasso.get().load(img).into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return descriptions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.desc);

        }
    }
}
