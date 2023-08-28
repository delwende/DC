
package com.aikinggroup.diabetecontrole.presenter;

import android.util.Log;

import com.aikinggroup.diabetecontrole.Constants;
import com.aikinggroup.diabetecontrole.activity.MainActivity;
import com.aikinggroup.diabetecontrole.db.DatabaseHandler;


public class MainPresenter {

    private DatabaseHandler dB;

    public MainPresenter(MainActivity mainActivity, DatabaseHandler databaseHandler) {
        dB = databaseHandler;
        Log.i("msg::", "initiated dB object");
        if (Constants.app.currentUser()== null) {
            // if user doesn't exists start hello activity
            mainActivity.startHelloActivity();
        } else {
            // If user already exists, update user's preferred language and recreate MainActivity
        }
    }

    public boolean isdbEmpty() {
        return dB.getGlucoseReadings().size() == 0;
    }
}
