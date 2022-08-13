/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 4/12/19 1:45 PM
 */

package com.ula.gameapp.app.main.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Constraints;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.dhims.timerview.TimerTextView;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.drm.FrameworkMediaDrm;
import com.google.android.exoplayer2.drm.HttpMediaDrmCallback;
import com.google.android.exoplayer2.drm.UnsupportedDrmException;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.offline.FilteringManifestParser;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.ads.AdsLoader;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.manifest.DashManifestParser;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.hls.playlist.DefaultHlsPlaylistParserFactory;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.RandomTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.ErrorMessageProvider;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.snackbar.Snackbar;
import com.ula.gameapp.App;
import com.ula.gameapp.R;
import com.ula.gameapp.ViewModels.FootStepViewModel;
import com.ula.gameapp.ViewModels.PetViewModel;
import com.ula.gameapp.ViewModels.SettingsViewModel;
import com.ula.gameapp.core.helper.GIFHelper;
import com.ula.gameapp.core.helper.JConstants;
import com.ula.gameapp.core.helper.Paths;
import com.ula.gameapp.item.AppConfig;
import com.ula.gameapp.item.JAnimation;
import com.ula.gameapp.item.PetStatus;
import com.ula.gameapp.utils.JWorker;
import com.ula.gameapp.utils.enums.FileType;
import com.ula.gameapp.utils.enums.PlayStatus;
import com.ula.gameapp.utils.views.FontBodyTextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifImageView;

import static com.ula.gameapp.core.helper.JConstants.Consts.ABR_ALGORITHM_DEFAULT;
import static com.ula.gameapp.core.helper.JConstants.Consts.ABR_ALGORITHM_EXTRA;
import static com.ula.gameapp.core.helper.JConstants.Consts.ABR_ALGORITHM_RANDOM;
import static com.ula.gameapp.core.helper.JConstants.Consts.ACTION_VIEW_LIST;
import static com.ula.gameapp.core.helper.JConstants.Consts.AD_TAG_URI_EXTRA;
import static com.ula.gameapp.core.helper.JConstants.Consts.DRM_KEY_REQUEST_PROPERTIES_EXTRA;
import static com.ula.gameapp.core.helper.JConstants.Consts.DRM_LICENSE_URL_EXTRA;
import static com.ula.gameapp.core.helper.JConstants.Consts.DRM_MULTI_SESSION_EXTRA;
import static com.ula.gameapp.core.helper.JConstants.Consts.DRM_SCHEME_EXTRA;
import static com.ula.gameapp.core.helper.JConstants.Consts.DRM_SCHEME_UUID_EXTRA;
import static com.ula.gameapp.core.helper.JConstants.Consts.EXTENSION_EXTRA;
import static com.ula.gameapp.core.helper.JConstants.Consts.EXTENSION_LIST_EXTRA;
import static com.ula.gameapp.core.helper.JConstants.Consts.KEY_AUTO_PLAY;
import static com.ula.gameapp.core.helper.JConstants.Consts.KEY_POSITION;
import static com.ula.gameapp.core.helper.JConstants.Consts.KEY_TRACK_SELECTOR_PARAMETERS;
import static com.ula.gameapp.core.helper.JConstants.Consts.KEY_WINDOW;
import static com.ula.gameapp.core.helper.JConstants.Consts.PREFER_EXTENSION_DECODERS_EXTRA;
import static com.ula.gameapp.core.helper.JConstants.Consts.URI_LIST_EXTRA;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment implements HomeContract.View, PlaybackPreparer,
        PlayerControlView.VisibilityListener, View.OnClickListener {

    private final static String TAG = HomeFragment.class.getSimpleName();

    @BindView(R.id.lnr_root)
    RelativeLayout lnrRoot;
    @BindView(R.id.tv_steps)
    TextView tvSteps;
    @BindView(R.id.lnr_details)
    LinearLayout lnrDetails;
    @BindView(R.id.txt_details)
    FontBodyTextView txtDetails;
    @BindView(R.id.iv_full_screen)
    ImageView ivFullScreen;
    @BindView(R.id.gif)
    GifImageView gifImageView;
    @BindView(R.id.img)
    ImageView imgImageView;
    @BindView(R.id.countdown_view)
    CountdownView countdownView;
    @BindView(R.id.movie_name_view)
    FontBodyTextView movieNameView;
    @BindView(R.id.donotclick_icon_view)
    ImageView doNotClickIcon;
    @BindView(R.id.timer_view)
    TimerTextView timerView;


    public HomeContract.Presenter mPresenter = null;

    private PetViewModel petViewModel;
    private PetStatus petStatus;
    private boolean playStatusComplete;
    private FootStepViewModel footStepViewModel;
    private boolean isLock;
    private AppConfig appConfig;
    private DecimalFormat formatter;

    // Player
//    @BindView(R.id.player_view) PlayerView playerView;

    @BindView(R.id.player_view)
    SurfaceView playerView;
    MediaPlayer mediaPlayer;


    private static final CookieManager DEFAULT_COOKIE_MANAGER;

    static {
        DEFAULT_COOKIE_MANAGER = new CookieManager();
        DEFAULT_COOKIE_MANAGER.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }

    private DataSource.Factory dataSourceFactory;
    private SimpleExoPlayer player;
    private FrameworkMediaDrm mediaDrm;
    private MediaSource mediaSource;
    private DefaultTrackSelector trackSelector;
    private DefaultTrackSelector.Parameters trackSelectorParameters;
    //    private DebugTextViewHelper debugViewHelper;
    private TrackGroupArray lastSeenTrackGroupArray;

    private boolean startAutoPlay = true;
    private int startWindow;
    private long startPosition;

    // Fields used only for ad playback. The ads loader is loaded via reflection.

    private AdsLoader adsLoader;
    private Uri loadedAdTagUri;
    private ViewGroup adUiViewGroup;
    private Intent animationIntent = null;
    private boolean isPrepared = false;

    private SharedPreferences sharedPreferences;

    private Bundle extras;

    public HomeFragment() {
        formatter = new DecimalFormat("#,###");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        extras = getArguments();
        sharedPreferences = getActivity().getSharedPreferences("ulaData", Context.MODE_PRIVATE);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);

        dataSourceFactory = buildDataSourceFactory();
        if (CookieHandler.getDefault() != DEFAULT_COOKIE_MANAGER) {
            CookieHandler.setDefault(DEFAULT_COOKIE_MANAGER);
        }

        v.setOnClickListener(this);
//        playerView.setControllerVisibilityListener(this);
//        playerView.setErrorMessageProvider(new PlayerErrorMessageProvider());
//        playerView.requestFocus();

        if (savedInstanceState != null) {
            trackSelectorParameters = savedInstanceState.getParcelable(KEY_TRACK_SELECTOR_PARAMETERS);
            startAutoPlay = savedInstanceState.getBoolean(KEY_AUTO_PLAY);
            startWindow = savedInstanceState.getInt(KEY_WINDOW);
            startPosition = savedInstanceState.getLong(KEY_POSITION);
        } else {
            trackSelectorParameters = new DefaultTrackSelector.ParametersBuilder().build();
            clearStartPosition();
        }

        return v;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter = new HomePresenter();
        mPresenter.setView(this);

        mediaPlayer = new MediaPlayer();
        SettingsViewModel settingsViewModel = new ViewModelProvider(this)
                .get(SettingsViewModel.class);
        settingsViewModel.getAppConfig().observe(getViewLifecycleOwner(), appConfig -> {
            if (appConfig != null) {
                this.appConfig = appConfig;
            }
        });

        petViewModel = new ViewModelProvider(requireActivity())
                .get(PetViewModel.class);
        petViewModel.getPetStatus().observe(requireActivity(), petStatus -> {
            if (petStatus != null) {
                this.petStatus = petStatus;
                try {
                    mPresenter.initial(petStatus,
                            petViewModel.getAnimationById(petStatus.getAnimationId()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                petViewModel.setPlayStatus(PlayStatus.PLAYING);
            }
        });

        footStepViewModel = new ViewModelProvider(this).get(FootStepViewModel.class);

        petViewModel.getTapsToGo().observe(getViewLifecycleOwner(), count -> {

            if (count != null && count > 0 && appConfig != null && appConfig.isDisplayTapToGo()) {
                String tapsToGo = count + " " + getString(R.string.taps_to_go);
                if (getActivity() != null) {
                    showSnackBar(getActivity(), tapsToGo);
                }
            }
        });

        petViewModel.getIsLock().observe(getViewLifecycleOwner(), isLock -> {
            this.isLock = isLock;
            if (!isLock) {
                countdownView.allShowZero();
                countdownView.setVisibility(View.GONE);
                toggleHelpView(false);
            }
        });

        petViewModel.getPlayStatus().observe(getViewLifecycleOwner(), playStatus -> {
            if (isLock) {
                if (petViewModel.getFileType(petStatus.getAnimationId()) == FileType.IMAGE) {
                    showCountdown(appConfig.getLockTime());
                    toggleHelpView(true);
                } else if (playStatus == PlayStatus.COMPLETE) {
                    showCountdown(appConfig.getLockTime());
                    toggleHelpView(true);
                }
            }
        });


        countdownView.setOnCountdownEndListener(cv -> {

            petViewModel.setIsLock(false);
        });


        playStatusComplete = true;

        /*StepViewModel stepViewModel = ViewModelProviders.of(getActivity()).get(StepViewModel.class);
        stepViewModel.getSteps().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer steps) {
                updateStepsData(steps);
            }
        });*/
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void updateStepsData(int steps) {
        tvSteps.setText(String.format("Today steps: %d", steps));
    }

//
//    @OnClick({R.id.img, R.id.gif})
//    public void holderClick(View v) {
//        mPresenter.onGifClicked();
//        mPresenter.decideGifUpdate();
//        //petViewModel.updatePetStatus(petStatus);
//
//        if (petStatus != null && !isLock && appConfig != null) {
//            PetStatus newPetStatus =
//                    petViewModel.getNextState(petStatus, mPresenter.getClickCount(), appConfig);
//            if (newPetStatus.getId() != petStatus.getId()) {
//                mPresenter.resetGifClick();
//            }
//            petViewModel.updatePetStatus(newPetStatus);
//        }
//    }

    @OnClick({R.id.img, R.id.player_view})
    public void holderClick(View v) {

        if (playStatusComplete) {

            mPresenter.onGifClicked();
            mPresenter.decideGifUpdate();
            //petViewModel.updatePetStatus(petStatus);

            int tapCount = petStatus.getTapCounter() - 1;
            petStatus.setTapCounter(tapCount);

            if (petStatus != null && !isLock && appConfig != null && tapCount <= 0) {
                PetStatus newPetStatus =
                        petViewModel.getNextState(petStatus, mPresenter.getClickCount(), appConfig);
                if (newPetStatus.getId() != petStatus.getId()) {
                    mPresenter.resetGifClick();
                }
                petViewModel.updatePetStatus(newPetStatus);

            }
        } else if (petStatus.getHasLoop()) {
            petStatus.setHasLoop(false);

            mPresenter.onGifClicked();
            mPresenter.decideGifUpdate();

            int tapCount = petStatus.getTapCounter() - 1;
            petStatus.setTapCounter(tapCount);
//            if (petStatus != null && !isLock && appConfig != null && tapCount <= 0) {
//                PetStatus newPetStatus =
//                        petViewModel.getNextState(petStatus, mPresenter.getClickCount(), appConfig);
//                if (newPetStatus.getId() != petStatus.getId()) {
//                    mPresenter.resetGifClick();
//                }
//                petViewModel.updatePetStatus(newPetStatus);
//
//            }

        } else if (petStatus.getMultiLoop()) {
            petStatus.setMultiLoop(false);
            petStatus.setAnimationId(petStatus.getEndId() + 1);
            mPresenter.onGifClicked();
            mPresenter.decideGifUpdate();

            int tapCount = petStatus.getTapCounter() - 1;
            petStatus.setTapCounter(tapCount);

            PetStatus newPetStatus =
                    petViewModel.getNextState(petStatus, mPresenter.getClickCount(), appConfig);
            petViewModel.updatePetStatus(newPetStatus);
        } else {

            doNotClickIcon.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    doNotClickIcon.setVisibility(View.INVISIBLE);
                }
            }, 2000);
        }
    }


    @SuppressLint("SourceLockedOrientationActivity")
    @OnClick({R.id.iv_full_screen})
    void fullScreenClick(View v) {
        if (getActivity() == null) return;
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            onOrientationChanged(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            onOrientationChanged(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    private void onOrientationChanged(int requestedOrientation) {
        int marginBottom = 0;

        if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            marginBottom = -50;
        }

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivFullScreen.getLayoutParams();
        params.setMargins(convertDpToPx(10), 0, convertDpToPx(10), convertDpToPx(marginBottom));
        ivFullScreen.setLayoutParams(params);
        ivFullScreen.bringToFront();

        gifImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        gifImageView.setAdjustViewBounds(true);
    }

    private int convertDpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
        return Math.round(pixels);
    }

    @Override
    public void toggleDrawableHolder(FileType fileType) {

        switch (fileType) {
            case IMAGE:
                imgImageView.setVisibility(View.VISIBLE);
                gifImageView.setVisibility(View.GONE);
                playerView.setVisibility(View.GONE);
                break;
            case GIF:
                imgImageView.setVisibility(View.GONE);
                gifImageView.setVisibility(View.VISIBLE);
                playerView.setVisibility(View.GONE);
                break;
            case MOVIE:
                imgImageView.setVisibility(View.GONE);
                gifImageView.setVisibility(View.GONE);
                playerView.setVisibility(View.VISIBLE);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void showDrawable(JAnimation animation) throws IOException {

        Long animationFinishTime = sharedPreferences.getLong("animation-finish-time", 0);

        if (animationFinishTime > System.currentTimeMillis()) {


            movieNameView.setVisibility(View.VISIBLE);
            playerView.setVisibility(View.INVISIBLE);
            timerView.setVisibility(View.VISIBLE);
            timerView.setEndTime(animationFinishTime);

            new Handler().postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                movieNameView.setVisibility(View.VISIBLE);
                                playerView.setVisibility(View.VISIBLE);
                                showDrawable(getRandomAnimation(animation));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }, animationFinishTime - System.currentTimeMillis());


        } else {
            timerView.setVisibility(View.INVISIBLE);
            String animationData = sharedPreferences.getString("animationData", "{}");


            try {

                JSONObject obj = new JSONObject(animationData);
                JSONObject ageObj = obj.getJSONObject(animation.getAge().toString());
                JSONObject bodyShapeObj = ageObj.getJSONObject(animation.getBodyShape().toString());
                JSONObject scenarioObj = bodyShapeObj.getJSONObject(String.valueOf(animation.getScenario()));


                int animCount = scenarioObj.getInt("count");
                if (animation.isHasLock()) {
                    animCount++;
                    scenarioObj.put("count", animCount);
                }

                JSONArray timesList = scenarioObj.getJSONArray("times");

                JSONObject mD = new JSONObject();

                mD.put("name", animation.getFileName());
                mD.put("date", System.currentTimeMillis());
                timesList.put(mD);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("animationData", obj.toString());
                editor.apply();

            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                JSONObject animationObj = new JSONObject(animationData);
                if (!animationObj.has(animation.getFileName())) {
                    JSONObject jsonObject = new JSONObject();
                    List<Long> timesList = new ArrayList<Long>();
                    timesList.add(System.currentTimeMillis());
                    jsonObject.put("timesList", timesList);

                    animationObj.put(animation.getFileName(), jsonObject);
                }
                petStatus.setTapCounter(animation.getTapsCount());

                if (animation.getFileType() == FileType.IMAGE) {
                    movieNameView.setText("Image: " + animation.getFileName());
                    animationIntent = null;
                    try {
                        InputStream ims = lnrRoot.getContext().getAssets().open("Movies/" +
                                animation.getFileName());
                        Drawable d = Drawable.createFromStream(ims, null);
                        imgImageView.setImageDrawable(d);
                        petViewModel.setPlayStatus(PlayStatus.COMPLETE);

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else if (animation.getFileType() == FileType.GIF) {

                    movieNameView.setText("Gif: " + animation.getFileName());
                    animationIntent = null;
                    GIFHelper gif = new GIFHelper().setContext(lnrRoot.getContext()).setGifHolder(gifImageView);
                    gif.fromAssets("GIFS/" + animation.getFileName());
//          gif.setSpeed(ula.getSpeed());
                    gif.setRepeat(1);
                    gif.getDrawable().addAnimationListener(new AnimationListener() {
                        @Override
                        public void onAnimationCompleted(int loopNumber) {
                            petViewModel.setPlayStatus(PlayStatus.COMPLETE);
                        }
                    });
                    gif.play();
                } else if (animation.getFileType() == FileType.MOVIE) {

                    movieNameView.setText("Movie: " + animation.getFileName());



                    SurfaceHolder surfaceHolder = playerView.getHolder();
                    mediaPlayer.setDisplay(surfaceHolder);

                    mediaPlayer.reset();

                    AssetFileDescriptor afd = getActivity().getAssets().openFd("Movies/" + animation.getFileName());
                    Log.v("Movie", animation.getFileName());
                    mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    animationIntent = null;

                    setVideoSize();
                    playStatusComplete = false;
                    petStatus.setHasLoop(animation.getHasLoop());
                    int taps = animation.getTapsCount();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                            if (animation.isHasLock()) {
                                long futureTimestamp = System.currentTimeMillis() + (180 * 1000);
                                timerView.setEndTime(futureTimestamp);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putLong("animation-finish-time", futureTimestamp);
                                editor.apply();
                                playerView.setVisibility(View.INVISIBLE);
                                movieNameView.setVisibility(View.INVISIBLE);
                                timerView.setVisibility(View.VISIBLE);

                                new Handler().postDelayed(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    movieNameView.setVisibility(View.VISIBLE);
                                                    playerView.setVisibility(View.VISIBLE);
                                                    showDrawable(getRandomAnimation(animation));
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, futureTimestamp - System.currentTimeMillis());
//                                try {
//                                    mPresenter.initial(petStatus,getRandomAnimation(animation));
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }


                            } else if ((taps == 0 || animation.getHasLoop()) || petStatus.getMultiLoop()) {

                                mPresenter.onGifClicked();
                                mPresenter.decideGifUpdate();

                                PetStatus newPetStatus = petViewModel.getNextState(petStatus, mPresenter.getClickCount(), appConfig);

                                if (newPetStatus.getId() != petStatus.getId()) {
                                    mPresenter.resetGifClick();
                                }
                                petViewModel.updatePetStatus(newPetStatus);
                            } else {
                                playStatusComplete = true;
                                petViewModel.setPlayStatus(PlayStatus.COMPLETE);
                            }
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private JAnimation getRandomAnimation(JAnimation animation) {
        String animationData = sharedPreferences.getString("animationData", "{}");

        try {
            JSONObject obj = new JSONObject(animationData);
            JSONObject ageObj = obj.getJSONObject(animation.getAge().toString());
            JSONObject bodyShapeObj = ageObj.getJSONObject(animation.getBodyShape().toString());

            Iterator<String> keys = bodyShapeObj.keys();

            List<Integer> idList = new ArrayList<Integer>();

            int min = -1;
            boolean firstAnim = true;
            while (keys.hasNext()) {
                String key = keys.next();
                if (bodyShapeObj.get(key) instanceof JSONObject) {
                    // do something with jsonObject here
                    JSONObject scenarioObj = bodyShapeObj.getJSONObject(key);
                    int animCount = scenarioObj.getInt("count");
                    if (firstAnim) {
                        min = animCount;
                        firstAnim = false;
                    }
                    if (min > animCount) {
                        idList.clear();
                        min = animCount;
                    }

                    if (min == animCount)
                        idList.add(scenarioObj.getInt("startAnimId"));

                }
            }

            int random = idList.get(new Random().nextInt(idList.size()));
            petStatus.setAnimationId(random);
            PetStatus newPetStatus = petViewModel.getNextState(petStatus, mPresenter.getClickCount(), appConfig);

            if (newPetStatus.getId() != petStatus.getId()) {
                mPresenter.resetGifClick();
            }
            petViewModel.updatePetStatus(newPetStatus);

            return petViewModel.getAnimationById(random);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void setVideoSize() {

        // // Get the dimensions of the video
        int videoWidth = mediaPlayer.getVideoWidth();
        int videoHeight = mediaPlayer.getVideoHeight();
        float videoProportion = (float) videoWidth / (float) videoHeight;

        // Get the width of the screen
        int screenWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        int screenHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        float screenProportion = (float) screenWidth / (float) screenHeight;

        // Get the SurfaceView layout parameters
        android.view.ViewGroup.LayoutParams lp = playerView.getLayoutParams();
        if (videoProportion > screenProportion) {
            lp.width = screenWidth;
            lp.height = (int) ((float) screenWidth / videoProportion);
        } else {
            lp.width = (int) (videoProportion * (float) screenHeight);
            lp.height = screenHeight;
        }
        // Commit the layout parameters
        playerView.setLayoutParams(lp);
    }

    @Override
    public void toggleHelpView(boolean visible) {
        if (isLock) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            String lockMessage = "Take " + formatter.format(
                    petViewModel.getRemainingSteps(cal.getTime())) +
                    " steps to reach daily Target!";
            txtDetails.setText(lockMessage);

        } else {
            txtDetails.setText(getString(R.string.tap_egg));
        }
        lnrDetails.setVisibility((visible) ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showCountdown(long time) {
        if (time <= 0) return;

        // enable the lock
//        countdownView.setVisibility(View.VISIBLE);
        countdownView.start(time);
    }

    private void scheduleWork() {

        Constraints constraints = new Constraints.Builder()
                .build();

        OneTimeWorkRequest onLockEndWork = new OneTimeWorkRequest.Builder(JWorker.class)
                .setInitialDelay(countdownView.getRemainTime(), TimeUnit.MILLISECONDS)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance().beginUniqueWork(JWorker.TAG, ExistingWorkPolicy.KEEP,
                onLockEndWork).enqueue();
        Log.d("JWorker", "Enqueued");
    }

    public void showSnackBar(Activity activity, String message) {
        View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            if (playerView != null) {
                playerView.refreshDrawableState();
            }
//            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || player == null) {
            if (playerView != null) {
                ;
            }
//            initializePlayer();
            if (petStatus != null)
                petViewModel.updatePetStatus(petStatus);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            if (playerView != null) {
//                playerView.onPause();
            }
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (isLock) {
            scheduleWork();
        }

        if (Util.SDK_INT > 23) {
            if (playerView != null) {
//                playerView.onPause();
            }
            releasePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseAdsLoader();
    }

    @Override
    public void preparePlayback() {
//        initializePlayer();
    }

    @Override
    public void onVisibilityChange(int visibility) {

    }

    private void initializePlayer() {

        if (player == null && animationIntent != null) {
            Intent intent = animationIntent;
            String action = intent.getAction();
            Uri[] uris;
            String[] extensions;
            if (JConstants.ACTION.ACTION_AUDIO.equals(action)) {
                uris = new Uri[]{intent.getData()};
                extensions = new String[]{intent.getStringExtra(EXTENSION_EXTRA)};
            } else if (ACTION_VIEW_LIST.equals(action)) {
                String[] uriStrings = intent.getStringArrayExtra(URI_LIST_EXTRA);
                uris = new Uri[uriStrings.length];
                for (int i = 0; i < uriStrings.length; i++) {
                    uris[i] = Uri.parse(uriStrings[i]);
                }
                extensions = intent.getStringArrayExtra(EXTENSION_LIST_EXTRA);
                if (extensions == null) {
                    extensions = new String[uriStrings.length];
                }
            } else {
                showToast(getString(R.string.unexpected_intent_action, action));
//                finish();
                return;
            }
            if (!Util.checkCleartextTrafficPermitted(uris)) {
                showToast(R.string.error_cleartext_not_permitted);
                return;
            }
            if (Util.maybeRequestReadExternalStoragePermission(getActivity(), uris)) {
                // The player will be reinitialized if the permission is granted.
                return;
            }

            DefaultDrmSessionManager<FrameworkMediaCrypto> drmSessionManager = null;
            if (intent.hasExtra(DRM_SCHEME_EXTRA) || intent.hasExtra(DRM_SCHEME_UUID_EXTRA)) {
                String drmLicenseUrl = intent.getStringExtra(DRM_LICENSE_URL_EXTRA);
                String[] keyRequestPropertiesArray =
                        intent.getStringArrayExtra(DRM_KEY_REQUEST_PROPERTIES_EXTRA);
                boolean multiSession = intent.getBooleanExtra(DRM_MULTI_SESSION_EXTRA, false);
                int errorStringId = R.string.error_drm_unknown;
                if (Util.SDK_INT < 18) {
                    errorStringId = R.string.error_drm_not_supported;
                } else {
                    try {
                        String drmSchemeExtra = intent.hasExtra(DRM_SCHEME_EXTRA) ? DRM_SCHEME_EXTRA
                                : DRM_SCHEME_UUID_EXTRA;
                        UUID drmSchemeUuid = Util.getDrmUuid(intent.getStringExtra(drmSchemeExtra));
                        if (drmSchemeUuid == null) {
                            errorStringId = R.string.error_drm_unsupported_scheme;
                        } else {
                            drmSessionManager =
                                    buildDrmSessionManagerV18(
                                            drmSchemeUuid, drmLicenseUrl, keyRequestPropertiesArray, multiSession);
                        }
                    } catch (UnsupportedDrmException e) {
                        errorStringId = e.reason == UnsupportedDrmException.REASON_UNSUPPORTED_SCHEME
                                ? R.string.error_drm_unsupported_scheme : R.string.error_drm_unknown;
                    }
                }
                if (drmSessionManager == null) {
                    showToast(errorStringId);
//                    finish();
                    return;
                }
            }

            TrackSelection.Factory trackSelectionFactory;
            String abrAlgorithm = intent.getStringExtra(ABR_ALGORITHM_EXTRA);
            if (abrAlgorithm == null || ABR_ALGORITHM_DEFAULT.equals(abrAlgorithm)) {
                trackSelectionFactory = new AdaptiveTrackSelection.Factory();
            } else if (ABR_ALGORITHM_RANDOM.equals(abrAlgorithm)) {
                trackSelectionFactory = new RandomTrackSelection.Factory();
            } else {
                showToast(R.string.error_unrecognized_abr_algorithm);
//                finish();
                return;
            }

            boolean preferExtensionDecoders =
                    intent.getBooleanExtra(PREFER_EXTENSION_DECODERS_EXTRA, false);
            @DefaultRenderersFactory.ExtensionRendererMode int extensionRendererMode =
                    App.getInstance().useExtensionRenderers()
                            ? (preferExtensionDecoders ? DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER
                            : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON)
                            : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF;
            DefaultRenderersFactory renderersFactory =
                    new DefaultRenderersFactory(getContext(), extensionRendererMode);

            trackSelector = new DefaultTrackSelector(trackSelectionFactory);
            trackSelector.setParameters(trackSelectorParameters);
            lastSeenTrackGroupArray = null;

            player =
                    ExoPlayerFactory.newSimpleInstance(
                            /* context= */ getContext(), renderersFactory, trackSelector, drmSessionManager);
            player.addListener(new PlayerEventListener());
            player.setPlayWhenReady(startAutoPlay);
            player.addAnalyticsListener(new EventLogger(trackSelector));
//            playerView.setPlayer(player);
//            playerView.setPlaybackPreparer(this);
//            debugViewHelper = new DebugTextViewHelper(player, debugTextView);
//            debugViewHelper.start();

            MediaSource[] mediaSources = new MediaSource[uris.length];
            for (int i = 0; i < uris.length; i++) {
                mediaSources[i] = buildMediaSource(uris[i], extensions[i]);
            }
            mediaSource =
                    mediaSources.length == 1 ? mediaSources[0] : new ConcatenatingMediaSource(mediaSources);
            String adTagUriString = intent.getStringExtra(AD_TAG_URI_EXTRA);
            if (adTagUriString != null) {
                Uri adTagUri = Uri.parse(adTagUriString);
                if (!adTagUri.equals(loadedAdTagUri)) {
                    releaseAdsLoader();
                    loadedAdTagUri = adTagUri;
                }
                MediaSource adsMediaSource = createAdsMediaSource(mediaSource, Uri.parse(adTagUriString));
                if (adsMediaSource != null) {
                    mediaSource = adsMediaSource;
                } else {
                    showToast(R.string.ima_not_loaded);
                }
            } else {
                releaseAdsLoader();
            }
        }
        boolean haveStartPosition = startWindow != C.INDEX_UNSET;
        if (haveStartPosition) {
            player.seekTo(startWindow, startPosition);
        }
        player.prepare(mediaSource, !haveStartPosition, false);
//        updateButtonVisibilities();
    }

    private void showToast(int messageId) {
        showToast(getString(messageId));
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    private DefaultDrmSessionManager<FrameworkMediaCrypto> buildDrmSessionManagerV18(
            UUID uuid, String licenseUrl, String[] keyRequestPropertiesArray, boolean multiSession)
            throws UnsupportedDrmException {
        HttpDataSource.Factory licenseDataSourceFactory =
                App.getInstance().buildHttpDataSourceFactory();
        HttpMediaDrmCallback drmCallback =
                new HttpMediaDrmCallback(licenseUrl, licenseDataSourceFactory);
        if (keyRequestPropertiesArray != null) {
            for (int i = 0; i < keyRequestPropertiesArray.length - 1; i += 2) {
                drmCallback.setKeyRequestProperty(keyRequestPropertiesArray[i],
                        keyRequestPropertiesArray[i + 1]);
            }
        }
        releaseMediaDrm();
        mediaDrm = FrameworkMediaDrm.newInstance(uuid);
        return new DefaultDrmSessionManager<>(uuid, mediaDrm, drmCallback, null, multiSession);
    }

    private void releaseMediaDrm() {
        if (mediaDrm != null) {
            mediaDrm.release();
            mediaDrm = null;
        }
    }

    private class PlayerEventListener implements Player.EventListener {

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            if (playbackState == Player.STATE_READY) {
                setLoadingVisibility(View.GONE);
            }
//            if (playbackState == Player.STATE_ENDED) {
//                showControls();
//            }
//            updateButtonVisibilities();
        }

        @Override
        public void onPositionDiscontinuity(@Player.DiscontinuityReason int reason) {
            if (player.getPlaybackError() != null) {
                // The user has performed a seek whilst in the error state. Update the resume position so
                // that if the user then retries, playback resumes from the position to which they seeked.
//                updateStartPosition();
            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException e) {
            if (isBehindLiveWindow(e)) {
                clearStartPosition();
                initializePlayer();
            } /*else {
                updateStartPosition();
                updateButtonVisibilities();
                showControls();
            }*/
        }

        @Override
        @SuppressWarnings("ReferenceEquality")
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
//            updateButtonVisibilities();
            if (trackGroups != lastSeenTrackGroupArray) {
                MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
                if (mappedTrackInfo != null) {
                    if (mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_VIDEO)
                            == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                        showToast(R.string.error_unsupported_video);
                    }
                    if (mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_AUDIO)
                            == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                        showToast(R.string.error_unsupported_audio);
                    }
                }
                lastSeenTrackGroupArray = trackGroups;
            }
        }
    }

    private void setLoadingVisibility(int visibility) {
        if (visibility == View.VISIBLE) {
//            playerView.showController();
        }
    }

    private static boolean isBehindLiveWindow(ExoPlaybackException e) {
        if (e.type != ExoPlaybackException.TYPE_SOURCE) {
            return false;
        }
        Throwable cause = e.getSourceException();
        while (cause != null) {
            if (cause instanceof BehindLiveWindowException) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }

    private void clearStartPosition() {
        startAutoPlay = true;
        startWindow = C.INDEX_UNSET;
        startPosition = C.TIME_UNSET;
    }

    private void releaseAdsLoader() {
        if (adsLoader != null) {
            adsLoader.release();
            adsLoader = null;
            loadedAdTagUri = null;
//            playerView.getOverlayFrameLayout().removeAllViews();
        }
    }

    /**
     * Returns an ads media source, reusing the ads loader if one exists.
     */
    private @Nullable
    MediaSource createAdsMediaSource(MediaSource mediaSource, Uri adTagUri) {
        // Load the extension source using reflection so the demo app doesn't have to depend on it.
        // The ads loader is reused for multiple playbacks, so that ad playback can resume.
        try {
            Class<?> loaderClass = Class.forName("com.google.android.exoplayer2.ext.ima.ImaAdsLoader");
            if (adsLoader == null) {
                // Full class names used so the LINT.IfChange rule triggers should any of the classes move.
                // LINT.IfChange
                Constructor<? extends AdsLoader> loaderConstructor =
                        loaderClass
                                .asSubclass(AdsLoader.class)
                                .getConstructor(android.content.Context.class, android.net.Uri.class);
                // LINT.ThenChange(../../../../../../../../proguard-rules.txt)
                adsLoader = loaderConstructor.newInstance(this, adTagUri);
                adUiViewGroup = new FrameLayout(getContext());
                // The demo app has a non-null overlay frame layout.
//                playerView.getOverlayFrameLayout().addView(adUiViewGroup);
            }
            AdsMediaSource.MediaSourceFactory adMediaSourceFactory =
                    new AdsMediaSource.MediaSourceFactory() {
                        @Override
                        public MediaSource createMediaSource(Uri uri) {
                            return HomeFragment.this.buildMediaSource(uri);
                        }

                        @Override
                        public int[] getSupportedTypes() {
                            return new int[]{C.TYPE_DASH, C.TYPE_SS, C.TYPE_HLS, C.TYPE_OTHER};
                        }
                    };
            return new AdsMediaSource(mediaSource, adMediaSourceFactory, adsLoader, adUiViewGroup);
        } catch (ClassNotFoundException e) {
            // IMA extension not loaded.
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return buildMediaSource(uri, null);
    }

    @SuppressWarnings("unchecked")
    private MediaSource buildMediaSource(Uri uri, @Nullable String overrideExtension) {
        @C.ContentType int type = Util.inferContentType(uri, overrideExtension);
        switch (type) {
            case C.TYPE_DASH:
                return new DashMediaSource.Factory(dataSourceFactory)
                        .setManifestParser(
                                new FilteringManifestParser<>(new DashManifestParser(), getOfflineStreamKeys(uri)))
                        .createMediaSource(uri);
            case C.TYPE_SS:
                return new SsMediaSource.Factory(dataSourceFactory)
                        .setManifestParser(
                                new FilteringManifestParser<>(new SsManifestParser(), getOfflineStreamKeys(uri)))
                        .createMediaSource(uri);
            case C.TYPE_HLS:
                return new HlsMediaSource.Factory(dataSourceFactory)
                        .setPlaylistParserFactory(
                                new DefaultHlsPlaylistParserFactory(getOfflineStreamKeys(uri)))
                        .createMediaSource(uri);
            case C.TYPE_OTHER:
                return new ExtractorMediaSource.Factory(dataSourceFactory)
                        .setLoadErrorHandlingPolicy(new CustomErrorPolicy())
                        .createMediaSource(uri);
            default: {
                throw new IllegalStateException("Unsupported type: " + type);
            }
        }
    }

    private List<StreamKey> getOfflineStreamKeys(Uri uri) {
        return App.getInstance().getDownloadTracker().getOfflineStreamKeys(uri);
    }

    private final class CustomErrorPolicy extends DefaultLoadErrorHandlingPolicy {

        @Override
        public long getRetryDelayMsFor(
                int dataType,
                long loadDurationMs,
                IOException exception,
                int errorCount) {
            // Replace NoConnectivityException with the corresponding
            // exception for the used DataSource.
            if (exception instanceof HttpDataSource.HttpDataSourceException) {
                /*runOnUiThread(() -> {
                    if (player.getPlaybackState() == Player.STATE_BUFFERING) {
                        setLoadingVisibility(View.VISIBLE);
                    }
                });*/

                return 5000; // Retry every 5 seconds.
            } else {
                return C.TIME_UNSET; // Anything else is surfaced.
            }
        }

        @Override
        public int getMinimumLoadableRetryCount(int dataType) {
            return Integer.MAX_VALUE;
        }
    }

    /**
     * Returns a new DataSource factory.
     */
    private DataSource.Factory buildDataSourceFactory() {
        return App.getInstance().buildDataSourceFactory();
    }

    private class PlayerErrorMessageProvider implements ErrorMessageProvider<ExoPlaybackException> {

        @Override
        public Pair<Integer, String> getErrorMessage(ExoPlaybackException e) {
            String errorString = getString(R.string.error_generic);
            if (e.type == ExoPlaybackException.TYPE_RENDERER) {
                Exception cause = e.getRendererException();
                if (cause instanceof MediaCodecRenderer.DecoderInitializationException) {
                    // Special case for decoder initialization failures.
                    MediaCodecRenderer.DecoderInitializationException decoderInitializationException =
                            (MediaCodecRenderer.DecoderInitializationException) cause;
                    if (decoderInitializationException.decoderName == null) {
                        if (decoderInitializationException.getCause() instanceof MediaCodecUtil.DecoderQueryException) {
                            errorString = getString(R.string.error_querying_decoders);
                        } else if (decoderInitializationException.secureDecoderRequired) {
                            errorString = getString(R.string.error_no_secure_decoder);
                        } else {
                            errorString = getString(R.string.error_no_decoder);
                        }
                    } else {
                        errorString = getString(R.string.error_instantiating_decoder);
                    }
                }
            }
            return Pair.create(0, errorString);
        }
    }

    @Override
    public void onClick(View v) {

    }

    private void releasePlayer() {
        if (player != null) {
            updateTrackSelectorParameters();
//            updateStartPosition();
//            debugViewHelper.stop();
//            debugViewHelper = null;
            player.release();
            player = null;
            mediaSource = null;
            trackSelector = null;
        }
        releaseMediaDrm();
    }

    private void updateTrackSelectorParameters() {
        if (trackSelector != null) {
            trackSelectorParameters = trackSelector.getParameters();
        }
    }
}