package com.ula.gameapp.repository;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ula.gameapp.App;
import com.ula.gameapp.database.AppDatabase;
import com.ula.gameapp.database.DatabaseClient;
import com.ula.gameapp.database.dao.AnimationDao;
import com.ula.gameapp.database.dao.AppConfigDao;
import com.ula.gameapp.database.dao.FootStepDao;
import com.ula.gameapp.database.dao.PetDao;
import com.ula.gameapp.database.dao.PetStatusDao;
import com.ula.gameapp.item.AppConfig;
import com.ula.gameapp.item.FootStep;
import com.ula.gameapp.item.JAnimation;
import com.ula.gameapp.item.PetStatus;
import com.ula.gameapp.utils.AppExecutors;
import com.ula.gameapp.utils.PrimaryDataProducer;
import com.ula.gameapp.utils.enums.Age;
import com.ula.gameapp.utils.enums.BodyShape;
import com.ula.gameapp.utils.enums.EmotionType;
import com.ula.gameapp.utils.enums.FileType;
import com.ula.gameapp.utils.enums.LoadingState;
import com.ula.gameapp.utils.enums.PlayStatus;
import com.ula.gameapp.utils.results.PrimaryDataResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class DataRepository {
    private static final String TAG = DataRepository.class.getSimpleName();
    private static DataRepository instance;
    private AppExecutors appExecutors;
    private AnimationDao animationDao;
    private PetStatusDao petStatusDao;
    private PetDao petDao;
    private FootStepDao footStepDao;
    private AppConfigDao appConfigDao;
    private MutableLiveData<PrimaryDataResult> primaryDataResultLiveData;
    private MutableLiveData<List<FootStep>> footStepsMutableLiveData;
    private MutableLiveData<Integer> tapsToGo;
    private MutableLiveData<Boolean> isLockLiveDate;
    private Date startDate;
    private Date endDate;
    private MutableLiveData<PlayStatus> playStatusMutableLiveData;

    public static DataRepository getInstance() {

        if (instance == null) {
            instance = getSync();
        }

        return instance;
    }

    private static synchronized DataRepository getSync() {
        if (instance == null) {
            instance = new DataRepository();
        }
        return instance;
    }

    private DataRepository() {
        AppDatabase appDatabase = DatabaseClient.getInstance(App.getContext()).getAppDatabase();
        this.appExecutors = App.getInstance().getExecutor();
        animationDao = appDatabase.animationDao();
        petStatusDao = appDatabase.petStatusDao();
        petDao = appDatabase.petDao();
        footStepDao = appDatabase.footStepDao();
        appConfigDao = appDatabase.appConfigDao();
        primaryDataResultLiveData = new MutableLiveData<>();
        footStepsMutableLiveData = new MutableLiveData<>();
        tapsToGo = new MutableLiveData<>();
        isLockLiveDate = new MutableLiveData<>();
        playStatusMutableLiveData = new MutableLiveData<>();
    }

    public LiveData<PrimaryDataResult> loadPrimaryData(Context c) {

        PrimaryDataResult primaryDataResult = new PrimaryDataResult();
        primaryDataResult.setLoadingState(LoadingState.LOADING);
        primaryDataResultLiveData.setValue(primaryDataResult);

        if (animationDao.getAnimationsCount() > 0) {
            primaryDataResult.setLoadingState(LoadingState.DONE);
            primaryDataResultLiveData.setValue(primaryDataResult);
        } else {
            appExecutors.diskIO().execute(() -> {
                PrimaryDataProducer primaryDataProducer = new PrimaryDataProducer();
                ArrayList<JAnimation> mList = primaryDataProducer.getAnimationsList(c);
                animationDao.insertAll(mList);

                PetStatus lastStatus = new PetStatus();
                lastStatus.setId(1);
                lastStatus.setAnimationId(1);
                lastStatus.setLastAge(Age.EGG);
                lastStatus.setLastBodyShape(BodyShape.NORMAL);
                lastStatus.setRepeatCounter(1);
                petStatusDao.insert(lastStatus);

                PrimaryDataResult primaryDataResult1 = new PrimaryDataResult();
                primaryDataResult1.setLoadingState(LoadingState.DONE);
                primaryDataResultLiveData.postValue(primaryDataResult1);
            });

        }

        return primaryDataResultLiveData;
    }

    public LiveData<PetStatus> getPetStatus() {
        return petStatusDao.getLastStatus();
    }

    public JAnimation getAnimationById(int id) {
        return animationDao.getJAnimation(id);
    }

    public void updatePetStatus(PetStatus newStatus) {
        PetStatus petStatus = new PetStatus();
        petStatus.setId(newStatus.getId());
        petStatus.setAnimationId(newStatus.getAnimationId());
        petStatus.setLastAge(newStatus.getLastAge());
        petStatus.setLastBodyShape(newStatus.getLastBodyShape());
//        petStatus.setRepeatCounter(newStatus.getRepeatCounter());

        if (newStatus.getMultiLoop()) {
            petStatus.setMultiLoop(true);
            petStatus.setStartId(newStatus.getStartId());
            petStatus.setEndId(newStatus.getEndId());
//            petStatus.setRepeatCounter(0);
        }

        petStatusDao.insert(petStatus);
    }

    public LiveData<List<FootStep>> getStepsHistory(Date startDate, Date endDate) {

        this.startDate = startDate;
        this.endDate = endDate;
        footStepsMutableLiveData.setValue(footStepDao.getStepsHistory(startDate, endDate));
        /*DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS",
                Locale.getDefault());
        for (FootStep footStep :
                footStepDao.getStepsHistory(startDate, endDate)) {
            Log.d("STEP-FROM-DB", dateFormat.format(footStep.getDate()) + " "
                    + footStep.getStepCount());
        }*/
        return footStepsMutableLiveData;
    }

    public void insertStepsHistory(List<FootStep> stepsList) {

        appExecutors.diskIO().execute(() -> {

            try {
                footStepDao.insertStepsHistory(stepsList);
                if (startDate != null && endDate != null) {
                    footStepsMutableLiveData.postValue(
                            footStepDao.getStepsHistory(startDate, endDate));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public PetStatus getNextState(PetStatus petStatus, int clickCount, AppConfig appConfig) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
        Date endDate = calendar.getTime();
        int daysCount = appConfig.getEffectiveDays();
        calendar.add(Calendar.DAY_OF_YEAR, -1 * daysCount);
        Date startDate = calendar.getTime();

        List<FootStep> stepList = footStepDao.getStepsHistory(startDate, endDate);

        double presentationScore = getPresentationScore(stepList, appConfig);
        BodyShape newBodyShape = getBodyShape(presentationScore, petStatus.getLastAge());

        return getPetStatus(newBodyShape, petStatus, clickCount, presentationScore);
    }

    private double getPresentationScore(List<FootStep> stepList, AppConfig appConfig) {

        SharedPreferences pref = App.getContext()
                .getSharedPreferences("UlaSettings", MODE_PRIVATE);
        int dailyTarget = pref.getInt("daily_goal", 0);

        double min = dailyTarget - (dailyTarget * appConfig.getMinThreshold());
        double max = dailyTarget + (dailyTarget * appConfig.getMaxThreshold());

        int presentationScore = 0;

        for (FootStep step :
                stepList) {
            double performanceScore = 0;

            if (step.getStepCount() <= min) {
                performanceScore = -1;
            } else if (step.getStepCount() > min && step.getStepCount() < dailyTarget) {
                performanceScore = 0;
            } else if (step.getStepCount() >= dailyTarget && step.getStepCount() < max) {
                performanceScore = 1;
            } else if (step.getStepCount() >= max) {
                performanceScore = 1.5;
            }
            presentationScore += performanceScore;
        }

        return presentationScore;
    }

    private BodyShape getBodyShape(double presentationScore, Age age) {
        BodyShape bodyShape;

        if (age == Age.ADULT) {

            if (presentationScore <= -2) {
                bodyShape = BodyShape.FAT;
            } else if (presentationScore > -2 && presentationScore <= 0) {
                bodyShape = BodyShape.OVER_WEIGHT;
            } else if (presentationScore > 0 && presentationScore <= 1) {
                bodyShape = BodyShape.NORMAL;
            } else {
                bodyShape = BodyShape.FIT;
            }
        } else if (age == Age.CHILD) {

            if (presentationScore <= 0) {
                bodyShape = BodyShape.OVER_WEIGHT;
            } else {
                bodyShape = BodyShape.NORMAL;
            }
        } else {

            bodyShape = BodyShape.NONE;
        }

        return bodyShape;
    }

    private PetStatus getPetStatus(BodyShape bodyShape, PetStatus petStatus, int clickCount,
                                   double presentationScore) {

        PetStatus newStatus = new PetStatus();
        JAnimation currentAnimation = animationDao.getJAnimation(petStatus.getAnimationId());

        if(!petStatus.getMultiLoop())
            tapsToGo.postValue((currentAnimation.getRepeatCount() + 1) - clickCount);

        if (clickCount == (currentAnimation.getRepeatCount() + 1) && currentAnimation.isHasLock()) {
            isLockLiveDate.postValue(true);
            return petStatus;
        }


        if (petStatus.getMultiLoop()) {

            int nextId = petStatus.getAnimationId();

            if (nextId > petStatus.getEndId())
                nextId = petStatus.getStartId();
            else if(nextId == petStatus.getEndId())
                nextId = petStatus.getStartId()-1;


            JAnimation nextAnimation = animationDao.getJAnimation(
                    animationDao.getNextId(nextId));

            newStatus.setId(petStatus.getId() + 1);
            newStatus.setLastAge(nextAnimation.getAge());
            newStatus.setLastBodyShape(bodyShape);
            newStatus.setAnimationId(nextAnimation.getId());
            newStatus.setMultiLoop(petStatus.getMultiLoop());
            newStatus.setStartId(petStatus.getStartId());
            newStatus.setEndId(petStatus.getEndId());
            newStatus.setScenario(petStatus.getScenario());


        } else if (clickCount < (currentAnimation.getRepeatCount() + 1) || petStatus.getHasLoop()) {
            return petStatus;
        } else {
            JAnimation nextAnimation;

            if (currentAnimation.getAge() == Age.EGG) {

                nextAnimation = animationDao.getJAnimation(
                        animationDao.getNextId(currentAnimation.getId()));

                newStatus.setId(petStatus.getId() + 1);
                newStatus.setLastAge(nextAnimation.getAge());
                newStatus.setLastBodyShape(bodyShape);
                newStatus.setAnimationId(nextAnimation.getId());
                newStatus.setScenario(nextAnimation.getScenario());


                if (nextAnimation.getMultiLoop()) {
                    newStatus.setMultiLoop(true);
                    newStatus.setStartId(nextAnimation.getStartId());
                    newStatus.setEndId(nextAnimation.getEndId());
                }

            } else {

                nextAnimation = animationDao.getJAnimation(
                        animationDao.getNextId(currentAnimation.getId()));

                // Age changes to adult
                if (nextAnimation == null) {
                    bodyShape = getBodyShape(presentationScore, Age.ADULT);
                    nextAnimation = animationDao.getJAnimation(bodyShape, Age.ADULT,
                            currentAnimation.getId());
                }

                // Repeat age
                if (nextAnimation == null) {
                    nextAnimation = animationDao.getJAnimation(bodyShape, Age.ADULT,
                            animationDao.getLastAnimationId(Age.CHILD));
                }

                newStatus.setId(petStatus.getId() + 1);
                newStatus.setLastAge(nextAnimation.getAge());
                newStatus.setLastBodyShape(bodyShape);
                newStatus.setAnimationId(nextAnimation.getId());
                newStatus.setScenario(nextAnimation.getScenario());


                if (nextAnimation.getMultiLoop()) {
                    newStatus.setMultiLoop(true);
                    newStatus.setStartId(nextAnimation.getStartId());
                    newStatus.setEndId(nextAnimation.getEndId());
                }
            }

        }

        return newStatus;
    }

    public List<BodyShape> getAllBodyShape(Age age) {
        return animationDao.getAllBodyShape(age);
    }

    public List<EmotionType> getAllEmotioTypes(Age age) {
        return animationDao.getAllEmotion(age);
    }

    public List<String> getAllFileNames(Age age, BodyShape bodyShape) {
        return animationDao.getAllFileName(age, bodyShape);
    }

    public void loadPetStatus(String fileName, PetStatus currentStatus) {

        JAnimation jAnimation = animationDao.getJAnimation(fileName);

        PetStatus newStatus = new PetStatus();
        newStatus.setId(currentStatus.getId() + 1);
        newStatus.setAnimationId(jAnimation.getId());
        newStatus.setLastAge(jAnimation.getAge());
        newStatus.setLastBodyShape(jAnimation.getBodyShape());
        newStatus.setRepeatCounter(jAnimation.getRepeatCount());
        newStatus.setScenario(jAnimation.getScenario());
        updatePetStatus(newStatus);
    }

    public LiveData<AppConfig> getAppConfig() {
        return appConfigDao.getAppConfig();
    }

    public void setAppConfig(AppConfig appConfig) {
        appConfigDao.setAppConfig(appConfig);
    }

    public LiveData<Integer> getTapsToGo() {
        return tapsToGo;
    }

    public LiveData<Boolean> getIsLock() {
        return isLockLiveDate;
    }

    public void setIsLock(boolean isLock) {
        this.isLockLiveDate.postValue(isLock);
    }

    public LiveData<PlayStatus> getPlayStatus() {
        return playStatusMutableLiveData;
    }

    public void setPlayStatus(PlayStatus playStatus) {
        this.playStatusMutableLiveData.setValue(playStatus);
    }

    public FileType getFileType(int animationId) {
        return animationDao.getFileType(animationId);
    }


    public void onLockEnd() {

        PetStatus currentStatus = petStatusDao.getCurrentStatus();
        AppConfig appConfig = appConfigDao.getCurrentConfig();

        if (currentStatus == null) return;
        if (appConfig == null) return;

        int repeatCount = animationDao.getJAnimation(currentStatus.getAnimationId())
                .getRepeatCount() + 1;
        PetStatus nextStatus = getNextState(currentStatus, repeatCount, appConfig);

        if (nextStatus != null) {
            setIsLock(false);
            updatePetStatus(nextStatus);
        }
    }

    public int getRemainingSteps(Date date) {

        int takenSteps = footStepDao.getStepsOfDate(date);
        SharedPreferences pref = App.getContext()
                .getSharedPreferences("UlaSettings", MODE_PRIVATE);
        int dailyTarget = pref.getInt("daily_goal", 0);

        return dailyTarget - takenSteps;
    }
}
