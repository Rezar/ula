/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 3/29/19 1:21 AM
 */

package com.ula.gameapp.core.helper;

import android.app.Activity;
import android.widget.ImageView;

public class AnimationHelper {
    private Activity activity;
    private ImageView holder;

    private String prefix;
    private int from;
    private int to;
    private int speed;
    private Operator operator;

    private int counter = 0;
    private String packageName;

    public enum Operator {
        Forward, Backward
    }

    public AnimationHelper(Activity activity, String prefix, ImageView holder, int from, int to, int speed, Operator operator) {
        this.activity = activity;
        this.prefix = prefix;
        this.holder = holder;
        this.from = from;
        this.to = to;
        this.speed = speed;
        this.operator = operator;

        packageName = activity.getPackageName();
    }

    public void startAnimation() {
        // set start range
        counter = from;

        new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    try {
                        final int resID = activity.getResources().getIdentifier(prefix + counter, "drawable", packageName);

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                holder.setImageResource(resID);
                            }
                        });

                        counter = (operator == Operator.Forward) ? counter + 1 : counter - 1;
                        Thread.sleep(speed);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (counter != to);
            }
        }).start();
    }
}