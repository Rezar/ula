package com.ula.gameapp.core.helper;

public class JConstants {

    public interface ACTION {
        String ACTION_REVIEW_SUBMIT = "com.ula.gameapp.ACTION_REVIEW_SUBMIT";
        String ACTION_LOGIN_STATE = "com.ula.gameapp.loginstate";
        String ACTION_COMMENTS_COUNT = "com.ula.gameapp.ACTION_COMMENTS_COUNT";
        String ACTION_AVERAGE_RATE = "com.ula.gameapp.ACTION_AVERAGE_RATE";
        String ACTION_USER_PROFILE_CHANGED = "com.ula.gameapp.ACTION_USER_PROFILE_CHANGED";
        String ACTION_CHECK_SUBSCRIPTION = "com.ula.gameapp.CHECK_SUBSCRIPTION";
        String ACTION_CHECK_SUBS_DONE = "com.ula.gameapp.CHECK_SUBS_DONE";
        String ACTION_ADD_TO_LIBRARY = "add";
        String ACTION_REMOVE_FROM_LIBRARY = "remove";
        String ACTION_BUYING_SUBSCRIPTION = "IS_BUYING_SUBSCRIPTION";
        String ACTION_PLAYER_SERVICE_START = "com.ula.gameapp.PLAYER_SERVICE_START";
        String ACTION_PLAYER_SERVICE_STOP = "com.ula.gameapp.PLAYER_SERVICE_STOP";
        String ACTION_VIEW = "android.intent.action.VIEW";
        String ACTION_PURCHASED_BOOKS_CHANGE = "com.ula.gameapp.PURCHASED_BOOKS_CHANGE";
        String ACTION_REVIEW_LIKE_DISLIKE = "com.ula.gameapp.ACTION_REVIEW_LIKE_DISLIKE";
        String ACTION_PURCHASED_PRODUCTS = "com.ula.gameapp.ACTION_PURCHASED_PRODUCTS";
        String ACTION_PRODUCT_RATE = "com.ula.gameapp.ACTION_PRODUCT_RATE";
        String ACTION_PRODUCT_REVIEWS = "com.ula.gameapp.ACTION_PRODUCT_REVIEWS";
        String ACTION_SUBSCRIPTION_STATUS = "com.ula.gameapp.ACTION_SUBSCRIPTION_STATUS";
        String ACTION_MODIFY_SUBSCRIPTION_BOOKS = "com.ula.gameapp.ACTION_MODIFY_SUBSCRIPTION_BOOKS";
        String ACTION_IS_PURCHASED = "com.ula.gameapp.ACTION_IS_PURCHASED";
        String ACTION_START_DOWNLOAD = "com.ula.gameapp.ACTION_START_DOWNLOAD";
        String ACTION_DOWNLOAD_PROGRESS = "com.ula.gameapp.ACTION_DOWNLOAD_PROGRESS";
        String ACTION_DOWNLOAD_CANCELED = "com.ula.gameapp.ACTION_DOWNLOAD_CANCELED";
        String ACTION_DOWNLOAD_COMPLETED = "com.ula.gameapp.ACTION_DOWNLOAD_COMPLETED";
        String ACTION_DOWNLOAD_STARTED = "com.ula.gameapp.ACTION_DOWNLOAD_STARTED";
        String ACTION_NETWORK_STATE_CHANGE = "com.ula.gameapp.neworkstate";
        String ACTION_CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";
        String ACTION_FILTER_PRODUCTS = "com.ula.gameapp.ACTION_FILTER_PRODUCTS";
        String ACTION_PASSWORD_CHANGED = "com.ula.gameapp.ACTION_PASSWORD_CHANGED";
        String ACTION_PASSWORD_NOT_CHANGED = "com.ula.gameapp.ACTION_PASSWORD_NOT_CHANGED";
        String ACTION_FINISHED_FETCHING_DATA = "com.ula.gameapp.ACTION_FINISHED_FETCHING_DATA";
        String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
        String ACTION_DOWNLOAD_ERROR = "com.ula.gameapp.ACTION_DOWNLOAD_ERROR";
        String ACTION_USER_PROFILE_SYNC = "com.ula.gameapp.ACTION_USER_PROFILE_SYNC";
        String ACTION_FAVOURITES_CHANGED = "com.ula.gameapp.ACTION_FAVOURITES_CHANGED";
        String ACTION_REFRESH_CART = "com.ula.gameapp.ACTION_REFRESH_CART";
        String SHOW_REGISTER_FRAGMENT = "com.ula.gameapp.SHOW_REGISTER_FRAGMENT";

        String ACTION_AUDIO = "com.ula.gameapp.ACTION_AUDIO";
        String ACTION_PREVIOUS_CHAPTER = "com.ula.gameapp.ACTION_PREVIOUS_CHAPTER";
        String ACTION_NEXT_CHAPTER = "com.ula.gameapp.ACTION_NEXT_CHAPTER";
        String ACTION_SLEEP_TIMER = "com.ula.gameapp.ACTION_SLEEP_TIMER";
        String ACTION_NOTIFICATION_TAPPED = "com.ula.gameapp.ACTION_NOTIFICATION_TAPPED";
        String ACTION_MEDIA_BUFFERING = "com.ula.gameapp.ACTION_MEDIA_BUFFERING";
        String ACTION_FINISH_AUDIO_ACTIVITY = "com.ula.gameapp.ACTION_FINISH_AUDIO_ACTIVITY";
        String ACTION_SHOW_TOAST = "com.ula.gameapp.ACTION_SHOW_TOAST";
        String ACTION_UPDATE_SLEEP_TIMER = "com.ula.gameapp.ACTION_UPDATE_SLEEP_TIMER";
        String ACTION_FINISH_ACTIVITY = "FINISH_ACTIVITY";
        String ACTION_SEEK_TO_POSITION = "org.khanaapp.khanalibrary.ACTION_SEEK_TO_POSITION";
    }
    
    public interface Consts {
        String ACTION_AUDIO = "com.ula.gameapp.ACTION_AUDIO";
        String ACTION_MEDIA_BUFFERING = "com.ula.gameapp.ACTION_MEDIA_BUFFERING";
        String ACTION_FINISH_AUDIO_ACTIVITY = "com.ula.gameapp.ACTION_FINISH_AUDIO_ACTIVITY";
        String ACTION_SHOW_TOAST = "com.ula.gameapp.ACTION_SHOW_TOAST";
        String ACTION_FINISH_ACTIVITY = "FINISH_ACTIVITY";

        String LAST_SEEN_BOOK_ID = "com.ula.gameapp.LAST_SEEN_BOOK_ID";
        String EPUB = "Epub";
        String PDF = "PDF";
        String MP3 = "mp3";
        String MP3_EXTENSION = "kext";
        String Mp4 = "Mp4";
        String PHONE_NUMBER = "PHONE_NUMBER";
        String BOTTOM_NAV_WIDTH = "NAV_WIDTH";
        String FILTER_PRODUCTS_TYPE = "FILTER_PRODUCTS_TYPE_NEW";
        String DOWNLOAD_URL = "DOWNLOAD_URL";
        String SUBS_TYPE = "SUBS_TYPE";
        String SUBS_LAST_MODIFIED = "SUBS_LAST_MODIFIED";
        String R_COUNT = "R_COUNT";
        String IS_FETCHING_DATA = "IS_FETCHING_DATA";
        String OTP_REGEX = "[0-9]{1,6}";
        String PROMOTER_CODE = "PROMOTER_CODE";
        String UNIQUE_IDENTIFICATION = "UNIQUE_IDENTIFICATION";
        String USER_NOTIFICATION = "USER_NOTIFICATION";
        String MORE_PRODUCTS_TYPE = "MORE_PRODUCTS_TYPE";
        String EMAIL_OR_MOBILE = "EMAIL_OR_MOBILE";
        String MORE_PRODUCTS_LIST = "MORE_PRODUCTS_LIST";
        String ENTITY_NAME = "ENTITY_NAME";
        String MORE_BY_TYPE = "MORE_BY_TYPE";
        String MORE_BY_LIST = "MORE_BY_LIST";
        String KEEP_SCREEN_ON = "KEEP_SCREEN_ON";
        String MORE_PRODUCTS_COUNT = "MORE_PRODUCTS_COUNT";
        String PRODUCT_ID = "PRODUCT_ID";
        String RELEASE_NOTE_VERSION = "RELEASE_NOTE_VERSION";
        String RELEASE_NOTE_LIST = "RELEASE_NOTE_LIST";
        String IS_NEW_INSTALL = "IS_NEW_INSTALL";
        String PRODUCT = "PRODUCT";
        String REVIEW_LIST = "REVIEW_LIST";
        String BOOK_COVER_FILE_NAME = "BOOK_COVER_FILE_NAME";
        String JOB_SCHEDULER_TAG = "com.ula.gameapp.JOB_SCHEDULER_TAG";
        String PASSWORD = "PASSWORD";
        String DIALOG_MESSAGE = "MESSAGE";
        String DIALOG_POSITIVE_BUTTON = "POSITIVE_TITLE";
        String DIALOG_NEGATIVE_BUTTON = "NEGATIVE_TITLE";
        String SELECTED_BOOK = "SELECTED_BOOK";
        String SERVICE_NUMBER = "SERVICE_NUMBER";
        String THEME_TYPE = "THEME_TYPE";
        String SHOW_FILTER_TUTORIAL = "com.ula.gameapp.SHOW_FILTER_TUTORIAL";

        // Player
        String DRM_SCHEME_EXTRA = "drm_scheme";
        String DRM_LICENSE_URL_EXTRA = "drm_license_url";
        String DRM_KEY_REQUEST_PROPERTIES_EXTRA = "drm_key_request_properties";
        String DRM_MULTI_SESSION_EXTRA = "drm_multi_session";
        String PREFER_EXTENSION_DECODERS_EXTRA = "prefer_extension_decoders";

        String EXTENSION_EXTRA = "extension";

        String ACTION_VIEW_LIST =
                "com.ula.gameapp.action.VIEW_LIST";
        String URI_LIST_EXTRA = "uri_list";
        String EXTENSION_LIST_EXTRA = "extension_list";

        String AD_TAG_URI_EXTRA = "ad_tag_uri";

        String ABR_ALGORITHM_EXTRA = "abr_algorithm";
        String ABR_ALGORITHM_DEFAULT = "default";
        String ABR_ALGORITHM_RANDOM = "random";

        // For backwards compatibility only.
        String DRM_SCHEME_UUID_EXTRA = "drm_scheme_uuid";

        // Saved instance state keys.
        String KEY_TRACK_SELECTOR_PARAMETERS = "track_selector_parameters";
        String KEY_WINDOW = "window";
        String KEY_POSITION = "position";
        String KEY_AUTO_PLAY = "auto_play";

        String IS_LOCAL_BINDING = "IS_LOCAL_BINDING";
        String ARTWORK_PATH = "ARTWORK_PATH";
        String CHAPTER_NAME = "CHAPTER_NAME";
        String BOOK_NAME = "BOOK_NAME";
        String CHAPTER_INDEX = "CHAPTER_INDEX";
        String BOTTOM_MARGIN = "BOTTOM_MARGIN";
        String BOOK_ID = "BOOK_ID";
        String MINUTES = "MINUTES";
        String RESET_TIMER = "RESET_TIMER";
        String SLEEP_TIME_REMAINING = "SLEEP_TIME_REMAINING";
        String SPHERICAL_STEREO_MODE_EXTRA = "spherical_stereo_mode";
        String SPHERICAL_STEREO_MODE_MONO = "mono";
        String SPHERICAL_STEREO_MODE_TOP_BOTTOM = "top_bottom";
        String SPHERICAL_STEREO_MODE_LEFT_RIGHT = "left_right";
        String VIDEO_PRODUCT = "VIDEO_PRODUCT";
        String IS_SAMPLE = "IS_SAMPLE";
        String ERROR_COUNT = "ERROR_COUNT";
        String MESSAGE = "MESSAGE";
        String TIMER_UNTILL_FINISH = "TIMER_UNTILL_FINISH";
        String DOWNLOAD_SUBS_BOOK = "DOWNLOAD_SUBS_BOOK";
        String DOWNLOAD_BOOK = "DOWNLOAD_BOOK";
        String LIBRARY_SORT_STATE = "LIBRAY_SORT_INDEX";

        String NOTIFICATION_PRODUCT_ID = "NOTIFICATION_PRODUCT_ID";
        String PERIODIC_TASK_ID = "com.ula.gameapp.PERIODIC_TASK_ID";
        String FIREBASE_TOKEN = "com.ula.gameapp.FIREBASE_TOKEN";
    }
}
