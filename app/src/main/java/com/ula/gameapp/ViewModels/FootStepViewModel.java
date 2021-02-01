package com.ula.gameapp.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ula.gameapp.item.FootStep;
import com.ula.gameapp.repository.DataRepository;

import java.util.Date;
import java.util.List;

public class FootStepViewModel extends ViewModel {

    public FootStepViewModel() {
    }

    public LiveData<List<FootStep>> getStepsHistory(Date startDate, Date endDate) {
        return DataRepository.getInstance().getStepsHistory(startDate, endDate);
    }

    public void insertStepsHistory(List<FootStep> stepsList) {
        DataRepository.getInstance().insertStepsHistory(stepsList);
    }
}
