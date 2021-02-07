package com.example.cureai;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<String> descriptions;
    private LayoutInflater inflater;
    private ItemClickListener listener;

    Adapter(Context context, List<String> descriptions, ItemClickListener listener) {
        this.descriptions = descriptions;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String fullText = descriptions.get(position);

        holder.desc.setText(getPresentIllnessSubstring(fullText));

        holder.desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(fullText);
            }
        });
    }

    private String getPresentIllnessSubstring(String fullText) {
//        int startIndex = fullText.toUpperCase().indexOf("History of Present Illness:".toUpperCase());
//        if (startIndex == -1) {
//            return fullText;
        if (!(fullText.toUpperCase().contains("History of Present Illness:".toUpperCase()))) {
            return fullText;
        }
        String subDesc = fullText.split("(?i)History of Present Illness:")[1];

//        String subDesc = fullText.substring(startIndex);
        String[] suffixSplit = subDesc.split("\n\n");
        return suffixSplit[0].trim();

    }

    @Override
    public int getItemCount() {
        return descriptions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.desc);
        }
    }

    public interface ItemClickListener {
        void onItemClicked(String text);
    }
}
