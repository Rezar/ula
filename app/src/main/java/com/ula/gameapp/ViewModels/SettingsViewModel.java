package com.ula.gameapp.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ula.gameapp.item.AppConfig;
import com.ula.gameapp.repository.DataRepository;
import com.ula.gameapp.utils.enums.Age;
import com.ula.gameapp.utils.enums.BodyShape;

import java.util.ArrayList;
import java.util.List;

public class SettingsViewModel extends ViewModel {
    private Age age;
    private BodyShape bodyShape;
    private MutableLiveData<List<Age>> ageListLiveData;
    private MutableLiveData<List<BodyShape>> bodyShapeLiveData;
    private MutableLiveData<List<String>> fileNamesLiveData;

    public SettingsViewModel() {
        ageListLiveData = new MutableLiveData<>();
        bodyShapeLiveData = new MutableLiveData<>();
        fileNamesLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Age>> getAgeList() {
        List<Age> ageList = new ArrayList<>();
        ageList.add(Age.EGG);
        ageList.add(Age.CHILD);
        ageList.add(Age.ADULT);
        ageListLiveData.setValue(ageList);
        return ageListLiveData;
    }

    public void setAge(Age age) {
        this.age = age;
        List<BodyShape> bodyShapeList = DataRepository.getInstance().getAllBodyShape(age);
        bodyShapeLiveData.setValue(bodyShapeList);
    }

    public LiveData<List<BodyShape>> getBodyShapeList() {
        return bodyShapeLiveData;
    }

    public void setBodyShape(BodyShape bodyShape) {
        this.bodyShape = bodyShape;
        List<String> fileNameList = DataRepository.getInstance().getAllFileNames(age, bodyShape);
        fileNamesLiveData.setValue(fileNameList);
    }

    public LiveData<List<String>> getFileNameList() {
        return fileNamesLiveData;
    }

    public LiveData<AppConfig> getAppConfig() {
        return DataRepository.getInstance().getAppConfig();
    }

    public void setAppConfig(AppConfig appConfig) {
        DataRepository.getInstance().setAppConfig(appConfig);
    }
}
