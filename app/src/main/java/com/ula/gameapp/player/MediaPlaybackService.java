package com.ula.gameapp.player;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media.MediaBrowserServiceCompat;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.C.ContentType;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.drm.FrameworkMediaDrm;
import com.google.android.exoplayer2.drm.HttpMediaDrmCallback;
import com.google.android.exoplayer2.drm.UnsupportedDrmException;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator;
import com.google.android.exoplayer2.offline.FilteringManifestParser;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
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
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Util;
import com.ula.gameapp.App;
import com.ula.gameapp.R;
import com.ula.gameapp.core.helper.JConstants;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MediaPlaybackService extends MediaBrowserServiceCompat {

    private static final String MY_EMPTY_MEDIA_ROOT_ID = "empty_root_id";

    private MediaSessionCompat mMediaSession;
    private SimpleExoPlayer player;
    private static final String TAG = MediaPlaybackService.class.getSimpleName();
    public final IBinder mBinder = new LocalBinder();
    private Intent myIntent;
    private FrameworkMediaDrm mediaDrm;
    private DefaultTrackSelector trackSelector;
    private DefaultTrackSelector.Parameters trackSelectorParameters;
    private TrackGroupArray lastSeenTrackGroupArray;
    private boolean startAutoPlay;
    private int startWindow;
    private long startPosition;
    private DataSource.Factory mediaDataSourceFactory;
    private MediaSource mediaSource;

    private static final CookieManager DEFAULT_COOKIE_MANAGER;
    static {
        DEFAULT_COOKIE_MANAGER = new CookieManager();
        DEFAULT_COOKIE_MANAGER.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }

    private MediaSessionConnector mediaSessionConnector;
    private MediaMetadataCompat mediaMetadata;
    private float playerSpeed = (float) 1.0;
    private PlayerBroadcastReceiver playerBroadcastReceiver;

    @Override
    public void onCreate() {
        super.onCreate();

        mMediaSession = new MediaSessionCompat(getApplicationContext(), TAG);

        updateMetaData();

        setSessionToken(mMediaSession.getSessionToken());

        mMediaSession.setActive(true);

        if (CookieHandler.getDefault() != DEFAULT_COOKIE_MANAGER) {
            CookieHandler.setDefault(DEFAULT_COOKIE_MANAGER);
        }

        trackSelectorParameters = new DefaultTrackSelector.ParametersBuilder().build();
        clearStartPosition();

    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid,
                                 @Nullable Bundle rootHints) {

        return new BrowserRoot(MY_EMPTY_MEDIA_ROOT_ID, null);
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull
            Result<List<MediaBrowserCompat.MediaItem>> result) {
        result.sendResult(new ArrayList<>());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        this.myIntent = intent;

        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {

            }
        }

        mediaDataSourceFactory = buildDataSourceFactory();

        initializePlayer();


        if (player != null) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(C.USAGE_MEDIA)
                    .setContentType(C.CONTENT_TYPE_SPEECH)
                    .build();
            player.setAudioAttributes(audioAttributes, true);
        }


        mediaSessionConnector = new MediaSessionConnector(mMediaSession,
                new JPlaybackController());
        mediaSessionConnector.setPlayer(player, null);

        TimelineQueueNavigator queueNavigator = new TimelineQueueNavigator(mMediaSession) {

            @Override
            public MediaDescriptionCompat getMediaDescription(Player player, int windowIndex) {
                return mediaMetadata.getDescription();
            }
        };
        mediaSessionConnector.setQueueNavigator(queueNavigator);

        return super.onStartCommand(intent, flags, startId);
    }

    public void initializePlayer() {
        if (player == null) {
            Intent intent = myIntent;
            if (intent == null) return;

            String action = intent.getAction();
            Uri[] uris;
            String[] extensions;
            if (JConstants.Consts.ACTION_AUDIO.equals(action)) {
                uris = new Uri[] {intent.getData()};
                extensions = new String[] {intent.getStringExtra(JConstants.Consts
                        .EXTENSION_EXTRA)};
            } else if (JConstants.Consts.ACTION_VIEW_LIST.equals(action)) {
                String[] uriStrings = intent.getStringArrayExtra(JConstants.Consts
                        .URI_LIST_EXTRA);
                uris = new Uri[uriStrings.length];
                for (int i = 0; i < uriStrings.length; i++) {
                    uris[i] = Uri.parse(uriStrings[i]);
                }
                extensions = intent.getStringArrayExtra(JConstants.Consts
                        .EXTENSION_LIST_EXTRA);
                if (extensions == null) {
                    extensions = new String[uriStrings.length];
                }
            } else {
                showToast(getString(R.string.unexpected_intent_action, action));
                if (mediaSessionConnector != null) {
                    mediaSessionConnector.mediaSession.sendSessionEvent(
                            JConstants.ACTION.ACTION_FINISH_AUDIO_ACTIVITY, null);
                }
                return;
            }
            if (!Util.checkCleartextTrafficPermitted(uris)) {
                showToast(R.string.error_cleartext_not_permitted);
                return;
            }

            DefaultDrmSessionManager<FrameworkMediaCrypto> drmSessionManager = null;
            if (intent.hasExtra(JConstants.Consts.DRM_SCHEME_EXTRA) ||
                    intent.hasExtra(JConstants.Consts.DRM_SCHEME_UUID_EXTRA)) {
                String drmLicenseUrl = intent.getStringExtra(JConstants.Consts
                        .DRM_LICENSE_URL_EXTRA);
                String[] keyRequestPropertiesArray =
                        intent.getStringArrayExtra(JConstants.Consts
                                .DRM_KEY_REQUEST_PROPERTIES_EXTRA);
                boolean multiSession = intent.getBooleanExtra(JConstants.Consts
                        .DRM_MULTI_SESSION_EXTRA, false);
                int errorStringId = R.string.error_drm_unknown;
                try {
                    String drmSchemeExtra = intent.hasExtra(JConstants.Consts
                            .DRM_SCHEME_EXTRA) ? JConstants.Consts.DRM_SCHEME_EXTRA
                            : JConstants.Consts.DRM_SCHEME_UUID_EXTRA;
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
                if (drmSessionManager == null) {
                    showToast(errorStringId);
                    if (mediaSessionConnector != null) {
                        mediaSessionConnector.mediaSession.sendSessionEvent(
                                JConstants.ACTION.ACTION_FINISH_AUDIO_ACTIVITY, null);
                    }
                    return;
                }
            }

            TrackSelection.Factory trackSelectionFactory;
            String abrAlgorithm = intent.getStringExtra(JConstants.Consts
                    .ABR_ALGORITHM_EXTRA);
            if (abrAlgorithm == null || JConstants.Consts.ABR_ALGORITHM_DEFAULT.equals(abrAlgorithm)) {
                trackSelectionFactory = new AdaptiveTrackSelection.Factory();
            } else if (JConstants.Consts.ABR_ALGORITHM_RANDOM.equals(abrAlgorithm)) {
                trackSelectionFactory = new RandomTrackSelection.Factory();
            } else {
                showToast(R.string.error_unrecognized_abr_algorithm);
//                finish();
                if (mediaSessionConnector != null) {
                    mediaSessionConnector.mediaSession.sendSessionEvent(
                            JConstants.ACTION.ACTION_FINISH_AUDIO_ACTIVITY, null);
                }
                return;
            }

            boolean preferExtensionDecoders =
                    intent.getBooleanExtra(JConstants.Consts
                            .PREFER_EXTENSION_DECODERS_EXTRA, false);
            @DefaultRenderersFactory.ExtensionRendererMode int extensionRendererMode =
                    App.getInstance().useExtensionRenderers()
                            ? (preferExtensionDecoders ? DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER
                            : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON)
                            : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF;
            DefaultRenderersFactory renderersFactory =
                    new DefaultRenderersFactory(this, extensionRendererMode);

            trackSelector = new DefaultTrackSelector(trackSelectionFactory);
            trackSelector.setParameters(trackSelectorParameters);
            lastSeenTrackGroupArray = null;

            player =
                    ExoPlayerFactory.newSimpleInstance(
                            /* context= */ this, renderersFactory, trackSelector, drmSessionManager);
            player.addListener(new PlayerEventListener());
            player.setPlayWhenReady(startAutoPlay);
            player.addAnalyticsListener(new EventLogger(trackSelector));

            MediaSource[] mediaSources = new MediaSource[uris.length];
            for (int i = 0; i < uris.length; i++) {
                mediaSources[i] = buildMediaSource(uris[i], extensions[i]);
            }
            mediaSource =
                    mediaSources.length == 1 ? mediaSources[0] : new ConcatenatingMediaSource(mediaSources);

        }
        boolean haveStartPosition = startWindow != C.INDEX_UNSET;
        if (haveStartPosition) {
            player.seekTo(startWindow, startPosition);
        }
        player.prepare(mediaSource, !haveStartPosition, false);
    }

    private void playMedia() {
        if (player != null) {
            player.setPlayWhenReady(startAutoPlay);
        }
    }

    public void pauseMedia() {
        if (player != null) {
            player.setPlayWhenReady(false);
        }
    }

    public void fastForwardMedia() {
        if (player != null) {
            seekToPosition(player.getCurrentPosition() + 30000);
        }
    }

    public void rewindMedia() {
        if (player != null) {
            seekToPosition(player.getCurrentPosition() - 10000);
        }
    }

    private void stopMedia() {
        if (player != null) {

            if (mediaSessionConnector != null) {
                mediaSessionConnector.mediaSession.sendSessionEvent(
                        JConstants.ACTION.ACTION_FINISH_AUDIO_ACTIVITY, null);
            }
            player.stop();
            mediaSessionConnector.setPlayer(null, null);
            mMediaSession.setActive(false);
            releasePlayer();
        }
    }

    private void seekToPosition(long position) {
        if (isPlayerInitialized()) {
            player.seekTo(position);
        }
    }

    public boolean isPlayerInitialized() {
        return player != null;
    }

    public class LocalBinder extends Binder {
        public MediaPlaybackService getService() {
            return MediaPlaybackService.this;
        }
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
            if (playbackState == Player.STATE_ENDED) {
                pauseMedia();
                updateMetaData();
                seekToPosition(0);
            }

            Log.i(TAG + "State", String.valueOf(playbackState));
        }

        @Override
        public void onPositionDiscontinuity(@Player.DiscontinuityReason int reason) {
            if (player.getPlaybackError() != null) {
                // The user has performed a seek whilst in the error state. Update the resume position so
                // that if the user then retries, playback resumes from the position to which they seeked.
                updateStartPosition();
            }

            Log.i(TAG + "Disc" , String.valueOf(reason));
        }

        @Override
        public void onPlayerError(ExoPlaybackException e) {
            if (isBehindLiveWindow(e)) {
                clearStartPosition();
                initializePlayer();
            } else {
                updateStartPosition();
            }

            Log.e(TAG, e.getMessage());
        }

        @Override
        @SuppressWarnings("ReferenceEquality")
        public void onTracksChanged(TrackGroupArray trackGroups,
                                    TrackSelectionArray trackSelections) {
//            updateButtonVisibilities();
            if (trackGroups != lastSeenTrackGroupArray) {
                MappingTrackSelector.MappedTrackInfo mappedTrackInfo =
                        trackSelector.getCurrentMappedTrackInfo();
                if (mappedTrackInfo != null) {
                    if (mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_VIDEO)
                            == MappingTrackSelector.MappedTrackInfo
                            .RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                        showToast(R.string.error_unsupported_video);
                    }
                    if (mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_AUDIO)
                            == MappingTrackSelector.MappedTrackInfo
                            .RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                        showToast(R.string.error_unsupported_audio);
                    }
                }
                lastSeenTrackGroupArray = trackGroups;
            }
        }

        @Override
        public void onSeekProcessed() {
            updateMetaData();
        }

        @Override
        public void onLoadingChanged(boolean isLoading) {
            if (isLoading) Log.i(TAG, "loading");
        }
    }

    public void updateStartPosition() {
        if (player != null) {
            startAutoPlay = player.getPlayWhenReady();
            startWindow = player.getCurrentWindowIndex();
            startPosition = Math.max(0, player.getContentPosition());
        }
        /*if (audioBook != null && isPlayerInitialized()) {
            MediaPosition mediaPosition = new MediaPosition();
            mediaPosition.setBookId(Integer.parseInt(audioBook.getBookID()));
            mediaPosition.setPosition(player.getCurrentPosition());
            mediaPosition.setWindow(player.getCurrentWindowIndex());
            myDBHelper.insertOrUpdateMediaPosition(mediaPosition);
        }*/
    }

    private void clearStartPosition() {
        startAutoPlay = true;
        startWindow = C.INDEX_UNSET;
        startPosition = C.TIME_UNSET;
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

    @SuppressWarnings("unchecked")
    private MediaSource buildMediaSource(Uri uri, @Nullable String overrideExtension) {
        @ContentType int type = Util.inferContentType(uri, overrideExtension);
        switch (type) {
            case C.TYPE_DASH:
                return new DashMediaSource.Factory(mediaDataSourceFactory)
                        .setManifestParser(
                                new FilteringManifestParser(new DashManifestParser(), getOfflineStreamKeys(uri)))
                        .createMediaSource(uri);
            case C.TYPE_SS:
                return new SsMediaSource.Factory(mediaDataSourceFactory)
                        .setManifestParser(
                                new FilteringManifestParser(new SsManifestParser(), getOfflineStreamKeys(uri)))
                        .createMediaSource(uri);
            case C.TYPE_HLS:
                return new HlsMediaSource.Factory(mediaDataSourceFactory)
                        .setPlaylistParserFactory(
                                new DefaultHlsPlaylistParserFactory((List<StreamKey>) getOfflineStreamKeys(uri)))
                        .createMediaSource(uri);
            case C.TYPE_OTHER:
                return new ExtractorMediaSource.Factory(mediaDataSourceFactory)
                        .setLoadErrorHandlingPolicy(new CustomErrorPolicy())
                        .createMediaSource(uri);
            default: {
                throw new IllegalStateException("Unsupported type: " + type);
            }
        }
    }

    /** Returns a new DataSource factory. */
    private DataSource.Factory buildDataSourceFactory() {
        return App.getInstance().buildDataSourceFactory();
    }

    private List<?> getOfflineStreamKeys(Uri uri) {
        return App.getInstance().getDownloadTracker().getOfflineStreamKeys(uri);
    }

    private void showToast(int messageId) {
        if (mediaSessionConnector != null) {
            Bundle bundle = new Bundle();
            bundle.putString(JConstants.Consts.MESSAGE, getString(messageId));
            mediaSessionConnector.mediaSession.sendSessionEvent(
                    JConstants.ACTION.ACTION_SHOW_TOAST, bundle);
        }
    }

    private void showToast(String message) {
        if (mediaSessionConnector != null) {
            Bundle bundle = new Bundle();
            bundle.putString(JConstants.Consts.MESSAGE, message);
            mediaSessionConnector.mediaSession.sendSessionEvent(
                    JConstants.ACTION.ACTION_SHOW_TOAST, bundle);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {

        if (intent.getExtras() != null && intent.getExtras().getBoolean(
                JConstants.Consts.IS_LOCAL_BINDING, false)) {
            return mBinder;
        } else {
            return super.onBind(intent);
        }
    }

    public SimpleExoPlayer getPlayer() {
        return player;
    }

    private void updateMetaData() {

        MediaMetadataCompat.Builder metadataBuilder = new MediaMetadataCompat.Builder();

        mediaMetadata = metadataBuilder.build();

        mMediaSession.setMetadata(mediaMetadata);
    }

    @Override
    public void onDestroy() {

        stopMedia();

        unregisterReceiver(playerBroadcastReceiver);

        super.onDestroy();
    }

    private class JPlaybackController implements MediaSessionConnector.PlaybackController {

        @Override
        public long getSupportedPlaybackActions(@Nullable Player player) {
            return PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                    PlaybackStateCompat.ACTION_REWIND |
                    PlaybackStateCompat.ACTION_PLAY |
                    PlaybackStateCompat.ACTION_PAUSE |
                    PlaybackStateCompat.ACTION_FAST_FORWARD |
                    PlaybackStateCompat.ACTION_SKIP_TO_NEXT |
                    PlaybackStateCompat.ACTION_STOP |
                    PlaybackStateCompat.ACTION_SEEK_TO;
        }

        @Override
        public void onPlay(Player player) {
            playMedia();
            updateMetaData();
        }

        @Override
        public void onPause(Player player) {
            pauseMedia();
            updateMetaData();
        }

        @Override
        public void onSeekTo(Player player, long position) {
            seekToPosition(position);
        }

        @Override
        public void onFastForward(Player player) {
            fastForwardMedia();
        }

        @Override
        public void onRewind(Player player) {
            rewindMedia();
        }

        @Override
        public void onStop(Player player) {
            stopMedia();
            stopSelf();
        }

        @Override
        public void onSetShuffleMode(Player player, int shuffleMode) {

        }

        @Override
        public void onSetRepeatMode(Player player, int repeatMode) {

        }

        @Override
        public String[] getCommands() {
            return new String[0];
        }

        @Override
        public void onCommand(Player player, String command, Bundle extras, ResultReceiver cb) {
            Log.i(TAG, command);
        }
    }

    public boolean changePlaySpeed(float speedValue) {

        if (player != null) {
            PlaybackParameters parameters = new PlaybackParameters(speedValue, 1);
            player.setPlaybackParameters(parameters);
            playerSpeed = speedValue;
            updateMetaData();
            return true;
        } else {
            return false;
        }
    }

    private class PlayerBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == null) {
                return;
            }

            if (intent.getAction().equals(JConstants.ACTION.ACTION_SEEK_TO_POSITION)) {
                Bundle extras = intent.getExtras();
                if (player != null && extras != null) {
                    long pos = extras.getLong("POSITION");

                    seekToPosition(pos);
                    playMedia();
                }
            }
        }
    }

    private void releasePlayer() {
        if (player != null) {
            updateTrackSelectorParameters();
            updateStartPosition();

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

    private long calculatePositionPercentage() {
        long percent = 0;
        try {
            if (player != null) {
                percent = (player.getCurrentPosition() * 100) / player.getDuration();
            }
        } catch (Exception e) {
            Log.e("Calculate_Percentage", e.getMessage());
        }
        return percent;
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
                if (player.getPlaybackState() == Player.STATE_BUFFERING &&
                        mediaSessionConnector != null) {
                    mediaSessionConnector.mediaSession.sendSessionEvent(
                            JConstants.Consts.ACTION_MEDIA_BUFFERING, null);
                }
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
}
