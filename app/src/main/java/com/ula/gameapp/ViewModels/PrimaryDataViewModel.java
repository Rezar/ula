package com.ula.gameapp.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ula.gameapp.repository.DataRepository;
import com.ula.gameapp.utils.results.PrimaryDataResult;

public class PrimaryDataViewModel extends ViewModel {

    public LiveData<PrimaryDataResult> loadPrimaryData() {
        return DataRepository.getInstance().loadPrimaryData();
    }
}
