
package com.aikinggroup.diabetecontrole.presenter;

import android.util.Log;

import com.aikinggroup.diabetecontrole.activity.LoginActivity;
import com.aikinggroup.diabetecontrole.db.DatabaseHandler;
import com.aikinggroup.diabetecontrole.db.entity.UserEntity;
import com.aikinggroup.diabetecontrole.db.UserBuilder;


public class LoginPresenter {
    private final DatabaseHandler dB;


    public LoginPresenter(LoginActivity loginActivity, final DatabaseHandler dbHandler) {
        dB = dbHandler;
    }

    private void saveToDatabase(final int id, final String lastname, final String firstname, final String email, final String telephone,final String password
    ) {
        UserEntity userEntity = new UserBuilder()
                .setId(id)
                .setLastname(lastname)
                .setFirstname(firstname)
                .setPassword(password)
                .setEmail(email)
                .setTelephone(telephone)
                .createUser();
        Log.i("Saving user", String.valueOf(userEntity));
        dB.addUser(userEntity); // We use ADA range by default
    }

}
