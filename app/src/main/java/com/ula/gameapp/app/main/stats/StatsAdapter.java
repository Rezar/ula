/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 4/12/19 12:32 PM
 */

package com.ula.gameapp.app.main.stats;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ula.gameapp.R;
import com.ula.gameapp.item.FootStep;
import com.ula.gameapp.utils.views.FontBodyTextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.ViewHolder> {
    private List<FootStep> items;
    private int dailyGoal;
    private DecimalFormat formatter;
    private boolean displayStepsCount;

    StatsAdapter(int dailyGoal) {
        this.dailyGoal = dailyGoal;
        this.items = new ArrayList<>();
        this.formatter = (DecimalFormat) NumberFormat.getInstance();
        this.formatter.applyPattern("#,###");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stats, parent,
                false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FootStep item = items.get(position);

        if (item == null)
            return;

        // calculate progress
        int progress = 100 * item.getStepCount() / this.dailyGoal;
        if (progress >= 0 && progress <= 17) {
            holder.imgStickman0.setImageAlpha(255);
            holder.imgStickman1.setImageAlpha(50);
            holder.imgStickman2.setImageAlpha(50);
            holder.imgStickman3.setImageAlpha(50);
            holder.imgStickman4.setImageAlpha(50);
            holder.imgStickman5.setImageAlpha(50);

        } else if (progress > 17 && progress <= 34) {
            holder.imgStickman0.setImageAlpha(50);
            holder.imgStickman1.setImageAlpha(255);
            holder.imgStickman2.setImageAlpha(50);
            holder.imgStickman3.setImageAlpha(50);
            holder.imgStickman4.setImageAlpha(50);
            holder.imgStickman5.setImageAlpha(50);

        } else if (progress > 34 && progress <= 50) {
            holder.imgStickman0.setImageAlpha(50);
            holder.imgStickman1.setImageAlpha(50);
            holder.imgStickman2.setImageAlpha(255);
            holder.imgStickman3.setImageAlpha(50);
            holder.imgStickman4.setImageAlpha(50);
            holder.imgStickman5.setImageAlpha(50);

        } else if (progress > 50 && progress <= 68) {
            holder.imgStickman0.setImageAlpha(50);
            holder.imgStickman1.setImageAlpha(50);
            holder.imgStickman2.setImageAlpha(50);
            holder.imgStickman3.setImageAlpha(255);
            holder.imgStickman4.setImageAlpha(50);
            holder.imgStickman5.setImageAlpha(50);

        } else if (progress > 68 && progress <= 85) {
            holder.imgStickman0.setImageAlpha(50);
            holder.imgStickman1.setImageAlpha(50);
            holder.imgStickman2.setImageAlpha(50);
            holder.imgStickman3.setImageAlpha(50);
            holder.imgStickman4.setImageAlpha(255);
            holder.imgStickman5.setImageAlpha(50);

        } else if (progress > 85) {
            holder.imgStickman0.setImageAlpha(50);
            holder.imgStickman1.setImageAlpha(50);
            holder.imgStickman2.setImageAlpha(50);
            holder.imgStickman3.setImageAlpha(50);
            holder.imgStickman4.setImageAlpha(50);
            holder.imgStickman5.setImageAlpha(255);
        }

        holder.txtDay.setText(android.text.format.DateFormat.format("EEEE", item.getDate()));
        holder.progress.setProgress(progress);
        String stepCount = formatter.format(item.getStepCount()) + " Steps";
        holder.txtStepCount.setText(stepCount);

        if (displayStepsCount) {
            holder.txtStepCount.setVisibility(View.VISIBLE);
        } else {
            holder.txtStepCount.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(FootStep step) {
        items.add(step);
        notifyDataSetChanged();
    }

    public void add(List<FootStep> step) {
        items.addAll(step);
        notifyDataSetChanged();
    }

    void removeAll() {
        items.clear();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private FontBodyTextView txtDay;
        private ProgressBar progress;
        private ImageView imgStickman0;
        private ImageView imgStickman1;
        private ImageView imgStickman2;
        private ImageView imgStickman3;
        private ImageView imgStickman4;
        private ImageView imgStickman5;
        private TextView txtStepCount;

        ViewHolder(View itemView) {
            super(itemView);

            txtDay = itemView.findViewById(R.id.txt_day);
            progress = itemView.findViewById(R.id.seekbar_progress);
            imgStickman0 = itemView.findViewById(R.id.img_stickman0);
            imgStickman1 = itemView.findViewById(R.id.img_stickman1);
            imgStickman2 = itemView.findViewById(R.id.img_stickman2);
            imgStickman3 = itemView.findViewById(R.id.img_stickman3);
            imgStickman4 = itemView.findViewById(R.id.img_stickman4);
            imgStickman5 = itemView.findViewById(R.id.img_stickman5);
            txtStepCount = itemView.findViewById(R.id.txt_step_count);
        }
    }

    public void setDisplayStepsCount(boolean displayStepsCount) {
        this.displayStepsCount = displayStepsCount;
        notifyDataSetChanged();
    }
}