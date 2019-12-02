package com.angu.myapplication.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.angu.myapplication.R;
import com.angu.myapplication.data.Statistics;

class StatisticsViewHolder extends RecyclerView.ViewHolder {

    TextView textRank;
    TextView textName;
    TextView textLevel;
    TextView textAccuracy;
    TextView textDate;

    Statistics item;

    StatisticsViewHolder(View itemView) {
        super(itemView);

        textRank = itemView.findViewById(R.id.textStatisticsRank);
        textName = itemView.findViewById(R.id.textStatisticsName);
        textLevel = itemView.findViewById(R.id.textStatisticsLevel);
        textAccuracy = itemView.findViewById(R.id.textStatisticsAccuracy);
        textDate = itemView.findViewById(R.id.textStatisticsDate);
    }
}
