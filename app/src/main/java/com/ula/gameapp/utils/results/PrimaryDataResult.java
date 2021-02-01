package com.ula.gameapp.utils.results;

import com.ula.gameapp.utils.enums.LoadingState;

public class PrimaryDataResult {
    private LoadingState loadingState;

    public LoadingState getLoadingState() {
        return loadingState;
    }

    public void setLoadingState(LoadingState loadingState) {
        this.loadingState = loadingState;
    }
}
