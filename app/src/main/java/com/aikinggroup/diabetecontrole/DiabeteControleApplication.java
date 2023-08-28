package com.aikinggroup.diabetecontrole;



import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

import com.aikinggroup.diabetecontrole.activity.HelloActivity;
import com.aikinggroup.diabetecontrole.db.DatabaseHandler;
import com.aikinggroup.diabetecontrole.db.entity.UserEntity;
import com.aikinggroup.diabetecontrole.presenter.HelloPresenter;
import com.aikinggroup.diabetecontrole.tools.LocaleHelper;
import com.aikinggroup.diabetecontrole.tools.Preferences;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;


public class DiabeteControleApplication extends Application {
    private static DiabeteControleApplication sInstance;

    @Nullable
    private LocaleHelper localeHelper;

    @Nullable
    private Preferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        //Realm.init(this); // context, usually an Activity or Application
        //Constants.app = new App(new AppConfiguration.Builder(appId).build());
        initFont();
       // initLanguage();
    }

    @VisibleForTesting
    protected void initFont() {
        //TODO: convert of using new introduced class Preferences
        // Get Dyslexia preference and adjust font
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDyslexicModeOn = sharedPref.getBoolean("pref_font_dyslexia", false);

        if (isDyslexicModeOn) {
            setFont("fonts/opendyslexic.otf");
        } else {
            setFont("fonts/lato.ttf");
        }
    }

    @VisibleForTesting
    protected void initLanguage() {
        UserEntity userEntity = getDBHandler().getUser(1);
        if (userEntity != null) {
            checkBadLocale(userEntity);

            /*String languageTag = user.getPreferred_language();
            if (languageTag != null) {
                getLocaleHelper().updateLanguage(this, languageTag);
            }*/
        }
    }

    private void checkBadLocale(UserEntity userEntity) {
        Preferences preferences = getPreferences();
        boolean cleanLocaleDone = preferences.isLocaleCleaned();

        if (!cleanLocaleDone) {
            UserEntity updatedUserEntity = new UserEntity(userEntity);
            //updatedUser.setPreferred_language(null);
            //TODO: is it long operation? should we move it to separate thread?
            getDBHandler().updateUser(updatedUserEntity);
            preferences.saveLocaleCleaned();
        }
    }

    private void setFont(String font) {
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath(font)
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }





    @NonNull
    public DatabaseHandler getDBHandler() {
        return new DatabaseHandler(getApplicationContext());
    }


    @NonNull
    public DatabaseHandler getDBHandlerLogin() {
        return new DatabaseHandler(getApplicationContext(), true);
    }


    @NonNull
    public LocaleHelper getLocaleHelper() {
        if (localeHelper == null) {
            localeHelper = new LocaleHelper();
        }
        return localeHelper;
    }

    @NonNull
    public Preferences getPreferences() {
        if (preferences == null) {
            preferences = new Preferences(this);
        }

        return preferences;
    }

    public static DiabeteControleApplication getInstance() {
        if (sInstance == null) {
            sInstance = new DiabeteControleApplication();
        }
        return sInstance;
    }

    @NonNull
    public HelloPresenter createHelloPresenter(@NonNull final HelloActivity activity) {
        return new HelloPresenter(activity, getDBHandler());
    }
}
