package com.ula.gameapp.activitytracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ula.gameapp.R;
import com.ula.gameapp.ViewModels.PetViewModel;
import com.ula.gameapp.ViewModels.SettingsViewModel;
import com.ula.gameapp.item.AppConfig;
import com.ula.gameapp.item.FootStep;
import com.ula.gameapp.item.PetStatus;
import com.ula.gameapp.item.Ula;
import com.ula.gameapp.utils.Converter;
import com.ula.gameapp.utils.GifLogHelper;
import com.ula.gameapp.utils.enums.Age;
import com.ula.gameapp.utils.enums.BodyShape;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;


//Bavan Divaani-azar
public class DebugFragment extends Fragment {

    private DebugAdapter adapter;

    GifLogHelper gifLogHelper;
    Spinner spinnerScenario, spinnerAge, spinnerBody, spinnerEmotion;
    Button btnLoadSenario;

    private PetViewModel petViewModel;
    private SettingsViewModel settingsViewModel;
    private Age age = null;
    private PetStatus currentStatus;
    private AppConfig appConfig;

    public DebugFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_debug, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = getActivity().findViewById(R.id.rv_debug);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new DebugAdapter();

        spinnerScenario = getActivity().findViewById(R.id.spnr_scenario);
        spinnerAge = getActivity().findViewById(R.id.spnr_age);
        spinnerBody = getActivity().findViewById(R.id.spnr_body);
        spinnerEmotion = getActivity().findViewById(R.id.spnr_emption);
        btnLoadSenario = getActivity().findViewById(R.id.btn_load_senario);

        recyclerView.setAdapter(adapter);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ulaData", Context.MODE_PRIVATE);

        Handler handler = new Handler(); // new handler

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                String mobileData = sharedPreferences.getString("stepsData-0", "{}");
                String watchData = sharedPreferences.getString("stepsData-1", "{}");

                List<FootStep> stepList = new ArrayList<FootStep>();
                try {

                    JSONObject mobileObj = new JSONObject(mobileData);
                    JSONObject watchObj = new JSONObject(watchData);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    for (int i = 0; i < 7; i++) {
                        String date = cal.getTime().toString();
                        if (mobileObj.has(date)) {
                            JSONObject jsonObject = mobileObj.getJSONObject(date);
                            Log.v("data", jsonObject.toString());
                            FootStep footStep = new FootStep();
                            footStep.setType(0);
                            footStep.setTotalSteps(jsonObject.getInt("total_steps"));
                            footStep.setGoogleFitness(jsonObject.getInt("google_fitness"));
                            footStep.setDownstairs(jsonObject.getInt("downstairs"));
                            footStep.setUpstairs(jsonObject.getInt("upstairs"));
                            footStep.setSitting(jsonObject.getInt("sitting"));
                            footStep.setStanding(jsonObject.getInt("standing"));
                            footStep.setJogging(jsonObject.getInt("jogging"));
                            footStep.setWalking(jsonObject.getInt("walking"));
                            footStep.setDate(cal.getTime());
                            stepList.add(footStep);
                        }
                        if (watchObj.has(date)) {
                            JSONObject jsonObject = watchObj.getJSONObject(date);
                            Log.v("data", jsonObject.toString());
                            FootStep footStep = new FootStep();
                            footStep.setType(1);
                            footStep.setTotalSteps(jsonObject.getInt("total_steps"));
                            footStep.setGoogleFitness(jsonObject.getInt("google_fitness"));
                            footStep.setDownstairs(jsonObject.getInt("downstairs"));
                            footStep.setUpstairs(jsonObject.getInt("upstairs"));
                            footStep.setSitting(jsonObject.getInt("sitting"));
                            footStep.setStanding(jsonObject.getInt("standing"));
                            footStep.setJogging(jsonObject.getInt("jogging"));
                            footStep.setWalking(jsonObject.getInt("walking"));
                            footStep.setDate(cal.getTime());
                            stepList.add(footStep);
                        }
                        cal.add(Calendar.DAY_OF_MONTH, -1);

                    }

                    if (stepList.size() == 0) {
                        FootStep footStep = new FootStep();
                        footStep.setDate(cal.getTime());
                        footStep.setType(0);
                        footStep.setStepCount(0);
                        stepList.add(footStep);

                        FootStep footStep2 = new FootStep();
                        footStep2.setDate(cal.getTime());
                        footStep2.setStepCount(0);
                        footStep2.setType(1);
                        stepList.add(footStep2);
                    }
                    updateAdapter(stepList);

                } catch (
                        JSONException e) {
                    e.printStackTrace();
                }

                handler.postDelayed(this, 200);

            }
        }, 200);


        gifLogHelper = new GifLogHelper();
        gifLogHelper.loadMap(getContext());
        speed = gifLogHelper.checkSpeed("6_1.gif");
        applyChanges();


        petViewModel = new ViewModelProvider(this).get(PetViewModel.class);
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);


        settingsViewModel.getAgeList().observe(getViewLifecycleOwner(), ages -> {
            List<String> ageList = new ArrayList<>();
            for (Age age : ages) {
                ageList.add(age.name());
            }
            ArrayAdapter<String> agesAdapter = new ArrayAdapter<>(requireContext(),
                    android.R.layout.simple_spinner_dropdown_item, ageList);
            spinnerAge.setAdapter(agesAdapter);

        });

        settingsViewModel.getBodyShapeList().observe(getViewLifecycleOwner(), bodyShapes -> {

            if (bodyShapes != null) {
                List<String> bodyShapeList = new ArrayList<>();
                for (BodyShape bodyShape : bodyShapes) {
                    bodyShapeList.add(bodyShape.name());
                }
                ArrayAdapter<String> bodyShapesAdapter = new ArrayAdapter<>(requireContext(),
                        android.R.layout.simple_spinner_dropdown_item, bodyShapeList);
                spinnerBody.setAdapter(bodyShapesAdapter);
            }
        });

        settingsViewModel.getFileNameList().observe(getViewLifecycleOwner(), fileNames -> {

            if (fileNames != null) {
                ArrayAdapter<String> fileNamesAdapter = new ArrayAdapter<>(requireContext(),
                        android.R.layout.simple_spinner_dropdown_item, fileNames);
                spinnerScenario.setAdapter(fileNamesAdapter);
            }
        });


        petViewModel.getPetStatus().observe(getViewLifecycleOwner(), petStatus -> {

            if (petStatus != null) {
                currentStatus = petStatus;
            }
        });


    }

    double speed = 1;
    int selectedItem = 0;
    List<Ula> ulaList;

    private void applyChanges() {

        try {
            Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vp + ":" + 0);
            if (null != fragment)
//                ((HomePresenter) ((HomeFragment) fragment).mPresenter).loadUlaDrawable(ulaList.get(selectedItem));

                gifLogHelper.saveSpeed(ulaList.get(selectedItem).getFileName(), speed);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateAdapter(List<FootStep> steps) {
        adapter.removeAll();
        adapter.add(steps);
        adapter.notifyDataSetChanged();
    }

    @OnItemSelected({R.id.spnr_age, R.id.spnr_body, R.id.spnr_emption})
    public void onItemChanged(Spinner spinner, int position) {
//        String value = ((String) spinner.getAdapter().getItem(position)).toLowerCase();
//        spinnerMap.put(spinner.getId(), value);

//        UlaDao ulaDao = DatabaseClient.getInstance(getContext()).getAppDatabase().ulaDao();
//        ulaList = ulaDao.getByStatus(spinnerMap.get(R.id.spnr_age), spinnerMap.get(R.id.spnr_body), spinnerMap.get(R.id.spnr_emption));

        // update scenario dropdown
        /*List<String> list = new ArrayList<>();
        spinnerScenario.setAdapter(null);
        for (int o = 0; o < ulaList.size(); o++)
            list.add(o + "");*/

//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerScenario.setAdapter(dataAdapter);

        BodyShape bodyShape = BodyShape.NONE;

        if (spinner.getId() == R.id.spnr_age) {
            switch (position) {
                case 0:
                    age = Age.EGG;
                    break;
                case 1:
                    age = Age.CHILD;
                    break;
                case 2:
                    age = Age.ADULT;
                    break;
            }

            fillBodyShapes(age);
        }

        if (spinner.getId() == R.id.spnr_body) {

            String strBodyShape = spinner.getSelectedItem().toString();
            if (strBodyShape.equalsIgnoreCase(BodyShape.NORMAL.name())) {
                bodyShape = BodyShape.NORMAL;
            } else if (strBodyShape.equalsIgnoreCase(BodyShape.FIT.name())) {
                bodyShape = BodyShape.FIT;
            } else if (strBodyShape.equalsIgnoreCase(BodyShape.FAT.name())) {
                bodyShape = BodyShape.FAT;
            } else if (strBodyShape.equalsIgnoreCase(BodyShape.OVER_WEIGHT.name())) {
                bodyShape = BodyShape.OVER_WEIGHT;
            } else {
                bodyShape = BodyShape.NONE;
            }

            if (age == null) return;
            fillFileNames(age, bodyShape);
        }
    }

    @OnClick({R.id.btn_load_senario})
    void onClickLoadSenario(View view) {

        String fileName = spinnerScenario.getSelectedItem().toString();
        if (currentStatus != null && !TextUtils.isEmpty(fileName)) {
            petViewModel.loadPetStatus(fileName, currentStatus);
            petViewModel.setIsLock(false);

            new AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.loading_senario))
                    .setMessage(getString(R.string.senario_successfull))
                    .setPositiveButton(getString(R.string.ok), (dialog, which) -> {

                    })
                    .show();
        }
    }


    private void fillFileNames(Age age, BodyShape bodyShape) {

        List<String> fileNames = petViewModel.getAllFileNames(age, bodyShape);
        ArrayAdapter<String> fileNameAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, fileNames);
        spinnerScenario.setAdapter(fileNameAdapter);
    }

    @OnItemSelected({R.id.spnr_age})
    void onAgeChanged(Spinner spinner, int position) {

        Age age = Converter.ageFromName(spinner.getSelectedItem().toString());
        settingsViewModel.setAge(age);
    }

    @OnItemSelected({R.id.spnr_body})
    void onBodyShapeChanged(Spinner spinner, int position) {

        BodyShape bodyShape = Converter.bodyShapeFromName(spinner.getSelectedItem().toString());
        settingsViewModel.setBodyShape(bodyShape);
    }


    private void fillBodyShapes(Age age) {

        List<BodyShape> bodyShapeList = petViewModel.getAllBodyShapes(age);
        List<String> bodyShapeNames = new ArrayList<>();
        for (BodyShape bodyShape :
                bodyShapeList) {
            bodyShapeNames.add(bodyShape.name());
        }
        ArrayAdapter<String> bodyShapeAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, bodyShapeNames);
        spinnerBody.setAdapter(bodyShapeAdapter);
    }

}