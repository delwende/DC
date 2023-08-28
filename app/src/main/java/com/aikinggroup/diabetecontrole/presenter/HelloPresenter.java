
package com.aikinggroup.diabetecontrole.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.aikinggroup.diabetecontrole.Constants;
import com.aikinggroup.diabetecontrole.db.DatabaseHandler;
import com.aikinggroup.diabetecontrole.db.entity.UserEntity;
import com.aikinggroup.diabetecontrole.db.UserBuilder;
import com.aikinggroup.diabetecontrole.view.HelloView;


public class HelloPresenter {
    private final DatabaseHandler dB;
    private final HelloView helloView;

    private int id;
    private String name;

    public HelloPresenter(final HelloView helloView, final DatabaseHandler dbHandler) {
        this.helloView = helloView;
        dB = dbHandler;
    }

    public void loadDatabase() {
        id =1; // Id is always 1. We don't support multi-user (for now :D).
        name = "Test Account1"; //TODO: add input for name in Tips;
    }

    public void onNextClicked(String lastname, String firstname, String email, String telephone, String password) {
        //if (validateAge(email)) {
           // saveToDatabase(id, lastname,firstname, email, telephone, password );


            helloView.startMainView();
        /*} else {
            helloView.displayErrorWrongAge();
        }*/
    }
    public void onNextClickedold(String lastname, String firstname, String email, String telephone, String password,String age, String gender, String language, String country, int type, String unit) {
        if (validateAge(email)) {
            saveToDatabase(id, lastname,firstname, email, telephone, password);
            Constants.app.getEmailPassword().registerUserAsync(email, password, it -> {
                if (it.isSuccess()) {
                    Log.i("EXAMPLE", "Successfully registered user.");
                } else {
                    Log.e("EXAMPLE", "Failed to register user: " + it.getError().getErrorMessage());
                }
            });

            helloView.startMainView();
        } else {
            helloView.displayErrorWrongAge();
        }
    }
    private boolean validateAge(String age) {
        if (TextUtils.isEmpty(age)) {
            return false;
        } else if (!TextUtils.isDigitsOnly(age)) {
            return false;
        } else {
            int finalAge = Integer.parseInt(age);
            return finalAge > 0 && finalAge < 120;
        }
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
