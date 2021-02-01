/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 3/29/19 1:21 AM
 */

package com.ula.gameapp.core.helper;

import android.content.Context;

import java.io.File;
import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class GIFHelper {
    public static final int REPEAT_ONCE = 1;
    public static final int REPEAT_TWICE = 2;
    public static final int REPEAT_INFINITY = Integer.MAX_VALUE;

    private Context context;
    private GifImageView holder;
    private GifDrawable drawable;
    private int repeat = 1;
    private float speed = 1.0f;

    public GIFHelper setContext(Context context) {
        this.context = context;
        return this;
    }

    public GIFHelper setGifHolder(GifImageView gifHolder) {
        this.holder = gifHolder;
        return this;
    }

    public GIFHelper fromAssets(String name) {
        try {
            this.drawable = new GifDrawable(context.getAssets(), name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public GIFHelper fromResource(int resourceId) {
        try {
            drawable = new GifDrawable(context.getResources(), resourceId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public GIFHelper fromFile(String path) {
        try {
            File gifFile = new File(context.getFilesDir(), path);
            this.drawable = new GifDrawable(gifFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public GIFHelper setRepeat(int repeat) {
        this.repeat = repeat;
        return this;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public GifDrawable getDrawable() {
        return drawable;
    }

    public void setDrawable(GifDrawable drawable) {
        this.drawable = drawable;
    }

    public GIFHelper play() {
        if (context == null) throw new NullPointerException("Context == null");
        if (holder == null) throw new NullPointerException("holder == null");
        if (drawable == null) throw new NullPointerException("drawable == null");

        drawable.setLoopCount(this.repeat);
        drawable.setSpeed(speed);
        holder.setImageDrawable(this.drawable);
        return this;
    }
}
