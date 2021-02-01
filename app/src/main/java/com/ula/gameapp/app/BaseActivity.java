/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 3/28/19 11:21 AM
 */

package com.ula.gameapp.app;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.ula.gameapp.app.intro.IntroActivity;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    public void launchIntroIfNeeded() {
        if (!getSharedPreferences("UlaSettings", MODE_PRIVATE).getBoolean("intro",
                false)) {

            Intent intent = new Intent(this, IntroActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
