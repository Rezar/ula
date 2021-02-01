/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 2/7/19 1:55 PM
 */

package com.ula.gameapp.utils.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ula.gameapp.R;

public class CustomCheckBox extends LinearLayout {
    private ImageView imgCheck;
    private FontBodyTextView textView;
    private boolean checked = false;
    private CheckBoxListener listener;

    public CustomCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.custom_checkbox_layout, this);

        imgCheck = findViewById(R.id.img);
        textView = findViewById(R.id.textView);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomCheckBoxOptions, 0, 0);
        checked = a.getBoolean(R.styleable.CustomCheckBoxOptions_isChecked, false);
        imgCheck.setImageResource((checked) ? R.mipmap.square_tick : R.mipmap.square);
        textView.setText(a.getString(R.styleable.CustomCheckBoxOptions_titleText));
        a.recycle();

        imgCheck.setOnClickListener(onClickListener);
        textView.setOnClickListener(onClickListener);
    }

    OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            toggleCheckBox();
            if (listener != null) listener.OnCheckedChanged(checked);
        }
    };

    public CustomCheckBox(Context context) {
        this(context, null);
    }

    public CustomCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, null);
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public void setChecked(boolean isChecked) {
        checked = isChecked;
        imgCheck.setImageResource((isChecked) ? R.mipmap.square_tick : R.mipmap.square);
    }

    public void toggleCheckBox() {
        checked = !checked;
        imgCheck.setImageResource((checked) ? R.mipmap.square_tick : R.mipmap.square);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setListener(CheckBoxListener listener) {
        this.listener = listener;
    }

    public interface CheckBoxListener {
        void OnCheckedChanged(boolean check);
    }
}