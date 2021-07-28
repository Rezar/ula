package com.ula.gameapp.app.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ula.gameapp.R;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DebugFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

//Bavan Divaani-azar
    //reference : https://github.com/curiousily/TensorFlow-on-Android-for-Human-Activity-Recognition-with-LSTMs
public class DebugFragment extends Fragment {



    public DebugFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_debug, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ulaData", Context.MODE_PRIVATE);

        TextView downstairs=getActivity().findViewById(R.id.downstairs);
        TextView jogging=getActivity().findViewById(R.id.jogging);
        TextView sitting=getActivity().findViewById(R.id.sitting);
        TextView standing=getActivity().findViewById(R.id.standing);
        TextView upstairs=getActivity().findViewById(R.id.upstairs);
        TextView walking=getActivity().findViewById(R.id.walking);
        TextView steps=getActivity().findViewById(R.id.steps);


        Handler handler = new Handler(); // new handler

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int step0=sharedPreferences.getInt("0",0);
                downstairs.setText("downstairs : "+step0 +" steps");

                int step1=sharedPreferences.getInt("1",0);
                jogging.setText("jogging : "+step1 +" steps");

                int step2=sharedPreferences.getInt("2",0);
                sitting.setText("sitting : "+step2+" steps");

                int step3=sharedPreferences.getInt("3",0);
                standing.setText("standing : "+step3+" steps");

                int step4=sharedPreferences.getInt("4",0);
                upstairs.setText("upstairs : "+step4 +" steps");

                int step5=sharedPreferences.getInt("5",0);
                walking.setText("walking : "+step5 +" steps");

                int step6=sharedPreferences.getInt("steps",0);
                steps.setText("steps : "+step6 +" steps");


                handler.postDelayed(this, 200);

            }
        }, 200);
    }
}