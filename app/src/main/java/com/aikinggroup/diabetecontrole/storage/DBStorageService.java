package com.aikinggroup.diabetecontrole.storage;

import android.util.Log;

import com.aikinggroup.diabetecontrole.db.DatabaseHandler;
import com.aikinggroup.diabetecontrole.db.entity.UserEntity;
import com.aikinggroup.diabetecontrole.db.UserBuilder;

public class DBStorageService {

    private final DatabaseHandler dB;

    public DBStorageService(DatabaseHandler dB) {
        this.dB = dB;
    }

    public void saveToDatabase( final String fullname, final Boolean privacyChecked, final String email, final String telephone,final String password
    ) {
        UserEntity userEntity = new UserBuilder()
                .setName(fullname)
                .setPrivacyChecked(privacyChecked)
                .setPassword(password)
                .setEmail(email)
                .setTelephone(telephone)
                .createUser();
        Log.i("Saving user", String.valueOf(userEntity));
        dB.addUser(userEntity); // We use ADA range by default
    }
}
