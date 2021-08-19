package com.ula.gameapp.activitytracker;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ula.gameapp.R;
import com.ula.gameapp.item.FootStep;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class DebugAdapter extends RecyclerView.Adapter<DebugAdapter.ViewHolder> {

    private List<FootStep> items;
    private DecimalFormat formatter;
    private boolean displayStepsCount;

    DebugAdapter() {
        this.items = new ArrayList<>();
        this.formatter = (DecimalFormat) NumberFormat.getInstance();
        this.formatter.applyPattern("#,###");
    }

    @NonNull
    @Override
    public DebugAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_debug, parent,
                false);
        return new DebugAdapter.ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DebugAdapter.ViewHolder holder, int position) {
        FootStep item = items.get(position);


        holder.downstairs.setText("downstairs: "+item.getDownstairs());
        holder.jogging.setText("jogging: " +item.getJogging());
        holder.sitting.setText("sitting: "+item.getSitting());
        holder.standing.setText("standing:"+ item.getStanding());
        holder.upstairs.setText("upstairs:"+item.getUpstairs());
        holder.walking.setText("walking: "+item.getWalking());
        holder.steps.setText("total steps: " + item.getTotalSteps());
        holder.googleFitness.setText("google fitness: " + item.getGoogleFitness());
        holder.date.setText(android.text.format.DateFormat.format("EEEE", item.getDate()));
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

        TextView date,downstairs, jogging, sitting, standing, upstairs, walking, steps,googleFitness;

        ViewHolder(View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.date);
            downstairs = itemView.findViewById(R.id.downstairs);
            jogging = itemView.findViewById(R.id.jogging);
            sitting = itemView.findViewById(R.id.sitting);
            standing = itemView.findViewById(R.id.standing);
            upstairs = itemView.findViewById(R.id.upstairs);
            walking = itemView.findViewById(R.id.walking);
            steps = itemView.findViewById(R.id.steps);
            googleFitness=itemView.findViewById(R.id.google_fitness);

        }
    }

    public void setDisplayStepsCount(boolean displayStepsCount) {
        this.displayStepsCount = displayStepsCount;
        notifyDataSetChanged();
    }

}