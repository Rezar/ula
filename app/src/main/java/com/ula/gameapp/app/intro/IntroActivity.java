/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 3/28/19 11:10 AM
 */

package com.ula.gameapp.app.intro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ula.gameapp.R;
import com.ula.gameapp.core.Annotation;
import com.ula.gameapp.core.helper.GoogleFit;
import com.ula.gameapp.core.helper.PedometerManager;

import static com.ula.gameapp.core.helper.GoogleFit.REQUEST_OAUTH_REQUEST_CODE;

public class IntroActivity extends AppCompatActivity {
    public static int permissionRequestCount = 0;
    IntroHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_intro);

        helper = new IntroHelper(this, getSupportFragmentManager());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_OAUTH_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                helper.done(); // we got the permission, no need for further action
                PedometerManager.setPedometerType(getBaseContext(), Annotation.PEDOMETER_GOOGLE_FIT);
            } else {
                if (permissionRequestCount++ < 2) {
                    GoogleFit.initializeGoogleFit(IntroActivity.this); // ask again
                }
                else {
                    helper.done(); // user not grant fit permission, we tried ...
                }

            }
        }
    }
}