package com.ula.gameapp.activitytracker;

import android.annotation.SuppressLint;
import android.util.Pair;
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

    private List<Pair<FootStep,FootStep>> items;
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

        FootStep itemM = items.get(position).first;

        holder.downstairsM.setText("downstairs: " + itemM.getDownstairs());
        holder.joggingM.setText("jogging: " + itemM.getJogging());
        holder.sittingM.setText("sitting: " + itemM.getSitting());
        holder.standingM.setText("standing:" + itemM.getStanding());
        holder.upstairsM.setText("upstairs:" + itemM.getUpstairs());
        holder.walkingM.setText("walking: " + itemM.getWalking());
        holder.stepsM.setText("total steps: " + itemM.getTotalSteps());

        FootStep itemW = items.get(position).second;

        holder.downstairsW.setText("downstairs: " + itemW.getDownstairs());
        holder.joggingW.setText("jogging: " + itemW.getJogging());
        holder.sittingW.setText("sitting: " + itemW.getSitting());
        holder.standingW.setText("standing:" + itemW.getStanding());
        holder.upstairsW.setText("upstairs:" + itemW.getUpstairs());
        holder.walkingW.setText("walking: " + itemW.getWalking());
        holder.stepsW.setText("total steps: " + itemW.getTotalSteps());

        holder.googleFitness.setText("google fitness: " + itemM.getGoogleFitness());
        holder.date.setText("Date : "+android.text.format.DateFormat.format("EEEE", itemM.getDate()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(Pair<FootStep,FootStep> step) {
        items.add(step);
        notifyDataSetChanged();
    }

    public void add(List<Pair<FootStep,FootStep>> step) {
        items.addAll(step);
        notifyDataSetChanged();
    }

    void removeAll() {
        items.clear();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView date, downstairsM, joggingM, sittingM, standingM, upstairsM, walkingM, stepsM,downstairsW, joggingW, sittingW, standingW, upstairsW, walkingW,stepsW, googleFitness;

        ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            //for mobile
            downstairsM = itemView.findViewById(R.id.m_downstairs);
            joggingM = itemView.findViewById(R.id.m_jogging);
            sittingM = itemView.findViewById(R.id.m_sitting);
            standingM = itemView.findViewById(R.id.m_standing);
            upstairsM = itemView.findViewById(R.id.m_upstairs);
            walkingM = itemView.findViewById(R.id.m_walking);
            stepsM = itemView.findViewById(R.id.m_steps);
            //for watch
            downstairsW = itemView.findViewById(R.id.w_downstairs);
            joggingW = itemView.findViewById(R.id.w_jogging);
            sittingW = itemView.findViewById(R.id.w_sitting);
            standingW = itemView.findViewById(R.id.w_standing);
            upstairsW = itemView.findViewById(R.id.w_upstairs);
            walkingW = itemView.findViewById(R.id.w_walking);
            stepsW = itemView.findViewById(R.id.w_steps);

            googleFitness = itemView.findViewById(R.id.google_fitness);
        }
    }

    public void setDisplayStepsCount(boolean displayStepsCount) {
        this.displayStepsCount = displayStepsCount;
        notifyDataSetChanged();
    }

}