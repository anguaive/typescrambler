package com.angu.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.angu.myapplication.R;
import com.angu.myapplication.data.Statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsViewHolder> {

    private final List<Statistics> items;

    public StatisticsAdapter() {

        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public StatisticsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_statistics, parent, false);
        return new StatisticsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticsViewHolder holder, int position) {
        Statistics item = items.get(position);
        holder.textRank.setText(String.format(Locale.US, "#%d", position+1));
        holder.textName.setText(item.playerName);
        holder.textLevel.setText(String.format(Locale.US, "Level %d", item.levelReached));
        holder.textAccuracy.setText(String.format(Locale.US, "%.2f%%", item.accuracy));
        holder.textDate.setText(item.date);


        holder.item = item;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Statistics item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void update(List<Statistics> shoppingItems) {
        items.clear();
        items.addAll(shoppingItems);
        notifyDataSetChanged();
    }

}
