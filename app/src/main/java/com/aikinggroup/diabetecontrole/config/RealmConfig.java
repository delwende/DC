package com.aikinggroup.diabetecontrole.config;

import static com.aikinggroup.diabetecontrole.Constants.app;
import static com.aikinggroup.diabetecontrole.Constants.appId;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.aikinggroup.diabetecontrole.BuildConfig;
import com.aikinggroup.diabetecontrole.Constants;
import com.aikinggroup.diabetecontrole.db.entity.UserEntity;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.sync.MutableSubscriptionSet;
import io.realm.mongodb.sync.Subscription;
import io.realm.mongodb.sync.SyncConfiguration;

public class RealmConfig {
    private static SyncConfiguration mRealmConfig;
    private Context mContext;
    private Realm realm;

    public RealmConfig(Context context) {
        this.mContext = context;
        Realm.init(context);
        app = new App(new AppConfiguration.Builder(appId)
                .appName("DIABETECONTROLE")
                .build());
        this.realm = getNewRealmInstance();
    }

    public Realm getNewRealmInstance() {
        if (mRealmConfig == null) {
             mRealmConfig = new SyncConfiguration.Builder(app.currentUser())
                    .initialSubscriptions(new SyncConfiguration.InitialFlexibleSyncSubscriptions() {
                        @Override
                        public void configure(Realm realm, MutableSubscriptionSet subscriptions) {
                            // add a subscription with a name
                            subscriptions.add(Subscription.create("frogSubscription",
                                    realm.where(UserEntity.class)
                                            .equalTo("email", "delianeb1@gmail.com")));
                        }
                    })
                    .build();

        }
        return Realm.getInstance(mRealmConfig); // Automatically run migration if needed
    }
}
