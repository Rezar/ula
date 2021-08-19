/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 4/12/19 2:12 PM
 */

package com.ula.gameapp.app.main.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.ula.gameapp.R;
import com.ula.gameapp.ViewModels.PetViewModel;
import com.ula.gameapp.ViewModels.SettingsViewModel;
import com.ula.gameapp.core.Annotation;
import com.ula.gameapp.core.helper.PedometerManager;
import com.ula.gameapp.item.AppConfig;
import com.ula.gameapp.item.PetStatus;
import com.ula.gameapp.item.Ula;
import com.ula.gameapp.utils.Converter;
import com.ula.gameapp.utils.GifLogHelper;
import com.ula.gameapp.utils.enums.Age;
import com.ula.gameapp.utils.enums.BodyShape;
import com.ula.gameapp.utils.views.CustomCheckBox;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

import static com.ula.gameapp.core.helper.GoogleFit.REQUEST_OAUTH_REQUEST_CODE;

public class SettingFragment extends Fragment {
    @BindView(R.id.lnr_root)
    LinearLayout lnrRoot;
    @BindView(R.id.chb_fit)
    CustomCheckBox fitCheckBox;
    @BindView(R.id.chb_sensor)
    CustomCheckBox sensorCheckBox;

    @BindView(R.id.txt_gif_name)
    TextView txtGifName;

    @BindView(R.id.et_speed)
    EditText etSpeed;

    @BindView(R.id.txt_gif_speed_log)
    TextView txtGifLog;

    GifLogHelper gifLogHelper;


    @BindView(R.id.switch_display_steps)
    Switch switchDisplaySteps;
    @BindView(R.id.switch_display_tap_to_go)
    Switch switchTapToGo;
    @BindView(R.id.edit_max_threshold)
    EditText editMaxThreshold;
    @BindView(R.id.edit_min_threshold)
    EditText editMinThreshold;
    @BindView(R.id.edit_effective_days)
    EditText editEffectiveDays;
    @BindView(R.id.edit_lock_time)
    EditText editLockTime;

    private PetViewModel petViewModel;
    private SettingsViewModel settingsViewModel;
    private Age age = null;
    private PetStatus currentStatus;
    private AppConfig appConfig;

    public SettingFragment() {
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fitCheckBox.setListener(check -> {
            if (check) {
                fitCheckBox.setChecked(true);
                sensorCheckBox.setChecked(false);
               if( PedometerManager.setPedometerType(getContext(), Annotation.PEDOMETER_GOOGLE_FIT))
                Snackbar.make(lnrRoot, "Please restart the app", 2000).show();
            }
        });

        sensorCheckBox.setListener(check -> {
            if (check) {
                fitCheckBox.setChecked(false);
                sensorCheckBox.setChecked(true);
                if (PedometerManager.setPedometerType(getContext(), Annotation.PEDOMETER_SENSOR))
                    Snackbar.make(lnrRoot, "Please restart the app", 2000).show();
            }
        });

        if (PedometerManager.getPedometerType(getContext()) == Annotation.PEDOMETER_GOOGLE_FIT)
            fitCheckBox.toggleCheckBox();
        else
        sensorCheckBox.toggleCheckBox();

        gifLogHelper = new GifLogHelper();
        gifLogHelper.loadMap(getContext());
        speed = gifLogHelper.checkSpeed("6_1.gif");

        applyChanges();

        petViewModel = new ViewModelProvider(this).get(PetViewModel.class);
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);



        settingsViewModel.getAppConfig().observe(getViewLifecycleOwner(), appConfig -> {
            if (appConfig != null) {
                this.appConfig = appConfig;
                switchDisplaySteps.setChecked(appConfig.isDisplayStepCounts());
                switchTapToGo.setChecked(appConfig.isDisplayTapToGo());
                String maxThreshold = String.valueOf(Math.round(appConfig.getMaxThreshold() * 100));
                String minThreshold = String.valueOf(Math.round(appConfig.getMinThreshold() * 100));
                editMaxThreshold.setText(maxThreshold);
                editMinThreshold.setText(minThreshold);
                editEffectiveDays.setText(String.valueOf(appConfig.getEffectiveDays()));
                String lockTime = String.valueOf(appConfig.getLockTime() / 1000);
                editLockTime.setText(lockTime);
            }
        });

        petViewModel.getPetStatus().observe(getViewLifecycleOwner(), petStatus -> {

            if (petStatus != null) {
                currentStatus = petStatus;
            }
        });
    }

    double speed = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_OAUTH_REQUEST_CODE) {

            }
        }
    }

    boolean firstRun = true;
    int selectedItem = 0;
    List<Ula> ulaList;
    Map<Integer, String> spinnerMap = new HashMap<>();



    /*@OnItemSelected({R.id.spnr_scenario})
    public void onScenarioChanged(Spinner spinner, int position) {
        if (firstRun) {
            firstRun = false;
            return;
        }

        selectedItem = Integer.parseInt((String) spinner.getAdapter().getItem(position));
        speed = gifLogHelper.checkSpeed(ulaList.get(selectedItem).getFileName());
        applyChanges();
    }*/


    /*@OnClick({R.id.btn_speed_add, R.id.btn_speed_sub})
    public void btnSpeedChange(View v) {
        changeSpeed(v);
    }*/

    /*@OnTouch({R.id.btn_speed_add, R.id.btn_speed_sub})
    public boolean btnLongSpeedChange(final View v) {
        v.setOnTouchListener(new LongTouchIntervalListener(250) {
            @Override
            public void onTouchInterval() {
                changeSpeed(v);
            }
        });

        return true;
    }*/

    private void changeSpeed(View v) {
        switch (v.getId()) {
            case R.id.btn_speed_add:
                speed += 0.1;
                break;

            case R.id.btn_speed_sub:
                if (speed > 0.1) {
                    speed -= 0.1;
                }
                break;
        }

        applyChanges();
    }

    private void applyChanges() {
        etSpeed.setText(String.format("%s", new DecimalFormat("##.##").format(speed)));

        try {
            Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vp + ":" + 0);
            if (null != fragment)
//                ((HomePresenter) ((HomeFragment) fragment).mPresenter).loadUlaDrawable(ulaList.get(selectedItem));

                txtGifName.setText(ulaList.get(selectedItem).getFileName());
            gifLogHelper.saveSpeed(ulaList.get(selectedItem).getFileName(), speed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        gifLogHelper.populateLog(getContext(), txtGifLog);
    }


    @OnClick({R.id.btn_save})
    void onClickSave(View view) {

        if (!validateMaxThreshold()) return;
        if (!validateMinThreshold()) return;
        if (!validateDaysCount()) return;
        if (!validateLockTime()) return;

        if (appConfig != null) {
            appConfig.setDisplayStepCounts(switchDisplaySteps.isChecked());
            appConfig.setDisplayTapToGo(switchTapToGo.isChecked());
            float maxThreshold = (Float.parseFloat(editMaxThreshold.getText().toString().trim()) / 100);
            float minThreshold = (Float.parseFloat(editMinThreshold.getText().toString().trim()) / 100);
            appConfig.setMaxThreshold(maxThreshold);
            appConfig.setMinThreshold(minThreshold);
            appConfig.setEffectiveDays(Integer.parseInt(editEffectiveDays.getText().toString().trim()));
            long lockTime = Integer.parseInt(editLockTime.getText().toString().trim()) * 1000;
            appConfig.setLockTime(lockTime);
            settingsViewModel.setAppConfig(appConfig);
            new AlertDialog.Builder(requireContext())
                    .setMessage(getString(R.string.successfully_saved))
                    .setIconAttribute(android.R.attr.alertDialogIcon)
                    .setPositiveButton(getString(R.string.ok), (dialog, which) -> {

                    })
                    .show();
        }
    }

    private boolean validateMaxThreshold() {

        int maxThreshold;

        try {
            maxThreshold = Integer.parseInt(editMaxThreshold.getText().toString().trim());
        } catch (Exception e) {
            maxThreshold = -1;
        }

        if (maxThreshold < 0 || maxThreshold > 100) {
            new AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.error))
                    .setMessage(getString(R.string.max_threshold_not_correct))
                    .setIconAttribute(android.R.attr.alertDialogIcon)
                    .setPositiveButton(getString(R.string.ok), (dialog, which) -> {

                    })
                    .show();
            return false;
        }
        return true;
    }

    private boolean validateMinThreshold() {

        int minThreshold;

        try {
            minThreshold = Integer.parseInt(editMinThreshold.getText().toString().trim());
        } catch (Exception e) {
            minThreshold = -1;
        }

        if (minThreshold < 0 || minThreshold > 100) {
            new AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.error))
                    .setMessage(getString(R.string.min_threshold_not_correct))
                    .setIconAttribute(android.R.attr.alertDialogIcon)
                    .setPositiveButton(getString(R.string.ok), (dialog, which) -> {

                    })
                    .show();
            return false;
        }

        return true;
    }

    private boolean validateDaysCount() {

        int effectiveDays;

        try {
            effectiveDays = Integer.parseInt(editEffectiveDays.getText().toString().trim());
        } catch (Exception e) {
            effectiveDays = -1;
        }

        if (effectiveDays < 0 || effectiveDays > 100) {
            new AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.error))
                    .setMessage(getString(R.string.effective_days_not_correct))
                    .setIconAttribute(android.R.attr.alertDialogIcon)
                    .setPositiveButton(getString(R.string.ok), (dialog, which) -> {

                    })
                    .show();
            return false;
        }

        return true;
    }

    private boolean validateLockTime() {

        int lockTime;

        try {
            lockTime = Integer.parseInt(editLockTime.getText().toString().trim());
        } catch (Exception e) {
            lockTime = -1;
        }

        if (lockTime < 0) {
            new AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.error))
                    .setMessage(getString(R.string.lock_time_not_correct))
                    .setIconAttribute(android.R.attr.alertDialogIcon)
                    .setPositiveButton(getString(R.string.ok), (dialog, which) -> {

                    })
                    .show();
            return false;
        }

        return true;
    }
}