package com.ula.gameapp.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ula.gameapp.item.AppConfig;
import com.ula.gameapp.item.JAnimation;
import com.ula.gameapp.item.PetStatus;
import com.ula.gameapp.repository.DataRepository;
import com.ula.gameapp.utils.enums.Age;
import com.ula.gameapp.utils.enums.BodyShape;
import com.ula.gameapp.utils.enums.EmotionType;
import com.ula.gameapp.utils.enums.FileType;
import com.ula.gameapp.utils.enums.PlayStatus;

import java.util.Date;
import java.util.List;

public class PetViewModel extends ViewModel {

    public PetViewModel() {
    }

    public LiveData<PetStatus> getPetStatus() {
        return DataRepository.getInstance().getPetStatus();
    }

    public JAnimation getAnimationById(int id) {
        return DataRepository.getInstance().getAnimationById(id);
    }

    public void updatePetStatus(PetStatus lastStatus) {
        DataRepository.getInstance().updatePetStatus(lastStatus);
    }

    public PetStatus getNextState(PetStatus petStatus, int clickCount, AppConfig appConfig) {
        return DataRepository.getInstance().getNextState(petStatus, clickCount, appConfig);
    }

    public List<BodyShape> getAllBodyShapes(Age age) {
        return DataRepository.getInstance().getAllBodyShape(age);
    }

    public List<EmotionType> getAllEmotioTypes(Age age) {
        return DataRepository.getInstance().getAllEmotioTypes(age);
    }

    public List<String> getAllFileNames(Age age, BodyShape bodyShape) {
        return DataRepository.getInstance().getAllFileNames(age, bodyShape);
    }

    public void loadPetStatus(String fileName, PetStatus currentStatus) {
        DataRepository.getInstance().loadPetStatus(fileName, currentStatus);
    }

    public LiveData<Integer> getTapsToGo() {
        return DataRepository.getInstance().getTapsToGo();
    }

    public LiveData<Boolean> getIsLock() {
        return DataRepository.getInstance().getIsLock();
    }

    public void setIsLock(boolean isLock) {
        DataRepository.getInstance().setIsLock(isLock);
    }

    public LiveData<PlayStatus> getPlayStatus() {
        return DataRepository.getInstance().getPlayStatus();
    }

    public void setPlayStatus(PlayStatus playStatus) {
        DataRepository.getInstance().setPlayStatus(playStatus);
    }

    public FileType getFileType(int animationId) {
        return DataRepository.getInstance().getFileType(animationId);
    }

    public int getRemainingSteps(Date date) {
        return DataRepository.getInstance().getRemainingSteps(date);
    }
}
