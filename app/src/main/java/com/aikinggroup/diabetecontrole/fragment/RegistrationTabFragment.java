package com.aikinggroup.diabetecontrole.fragment;

import static io.realm.Realm.getApplicationContext;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.aikinggroup.diabetecontrole.Constants;
import com.aikinggroup.diabetecontrole.DiabeteControleApplication;
import com.aikinggroup.diabetecontrole.R;
import com.aikinggroup.diabetecontrole.activity.LoginActivity;
import com.aikinggroup.diabetecontrole.db.DatabaseHandler;
import com.aikinggroup.diabetecontrole.presenter.LoginPresenter;
import com.aikinggroup.diabetecontrole.storage.DBStorageService;
import com.aikinggroup.diabetecontrole.tools.LabelledSpinner;
import com.aikinggroup.diabetecontrole.tools.LocaleHelper;

import butterknife.BindView;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;

public class RegistrationTabFragment extends Fragment {

   /* private View pass;
    private View email;
    private View telephone;
    private View login;
    private View name;
    private View policy;*/

    @BindView(R.id.name)
    TextView name;


    @BindView(R.id.email)
    TextView email;

    @BindView(R.id.telephone)
    TextView telephone;

    @BindView(R.id.password)
    TextView pass;

    @BindView(R.id.policy)
    CheckBox policy;



    @BindView(R.id.patientLoginButton)
    Button patientLoginButton;

    private DatabaseHandler dB;
    private LocaleHelper localeHelper;
    private DBStorageService dbStorageService;
    private   DiabeteControleApplication app;


    @Override
    public ViewGroup onCreateView(LayoutInflater inflater, ViewGroup contenair, Bundle savedInstanceState){
        ViewGroup root =(ViewGroup) inflater.inflate(R.layout.registration_tab_fragment,contenair,false);

        email = root.findViewById(R.id.email);
        pass = root.findViewById(R.id.password);
        telephone = root.findViewById(R.id.telephone);
        patientLoginButton =root.findViewById(R.id.patientLoginButton);
        name =root.findViewById(R.id.name);
        policy=root.findViewById(R.id.policy);

        email.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        telephone.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        patientLoginButton.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();
        name.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();
        policy.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();

       app = (DiabeteControleApplication) getActivity().getApplicationContext();


        patientLoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Performing Validation by calling validation functions
                if(!validateName() | !validatePassword() | !validatePhoneNo() | !validateEmail() | !checkPolicy()){
                    return;
                }

                //Get all the values in String
                String fullname = name.getText().toString();
                String username = email.getText().toString();
                String phoneNo = telephone.getText().toString();
                String password = pass.getText().toString();
                boolean policychecked =policy.isChecked();

                Constants.app.getEmailPassword().registerUserAsync(username, password, it -> {
                    if (it.isSuccess()) {
                        Log.i("EXAMPLE", "Successfully registered user.");
                        showAlert("Account Created Successfully!", "Please verify your email before Login", false);
                        dB = app.getDBHandler();
                        dbStorageService = new DBStorageService( dB);
                        dbStorageService.saveToDatabase(fullname, policychecked,username,phoneNo,password);
                        // The user has previously created an email/password account

                    } else {
                        Log.e("EXAMPLE", "Failed to register user: " + it.getError().getErrorMessage());
                        showAlert("Error Account Creation failed", "Account could not be created" + " :" +it.getError().getErrorMessage(), true);

                    }
                });
       /* Intent intent = new Intent(getActivity().getApplicationContext(),VerifyPhoneNo.class);
        intent.putExtra("name",fullname);
        intent.putExtra("isChecked",policychecked);
        intent.putExtra("email",username);
        intent.putExtra("phoneNo",phoneNo);
        intent.putExtra("password",password);
        startActivity(intent);*/
                app.getDBHandler().closeRealm();


            }
        });




      /*  Constants.app.getEmailPassword().registerUserAsync(email, pass, it -> {
            if (it.isSuccess()) {
                Log.i("EXAMPLE", "Successfully registered user.");
            } else {
                Log.e("EXAMPLE", "Failed to register user: " + it.getError().getErrorMessage());
            }
        });*/
        return root;
    }


    private Boolean validateName() {

    String val = name.getText().toString();


        if (val.isEmpty()) {
            name.setError("Field cannot be empty");
            return false;
        } else {
            name.setError(null);
            return true;
        }
    }


    private Boolean validateEmail() {
        String val = email.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            email.setError("Invalid email address");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private Boolean validatePhoneNo() {
        String val = telephone.getText().toString();

        if (val.isEmpty()) {
            telephone.setError("Field cannot be empty");
            return false;
        } else {
            telephone.setError(null);
            return true;
        }
    }

    private Boolean checkPolicy(){
         if(!policy.isChecked()) {
        policy.setError("Policy must be accepted");
             return false;
         }else {
             policy.setChecked(false);
             return true;

         }
    }
    private Boolean validatePassword() {
        String val = pass.getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            pass.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            pass.setError("Password is too weak");
            return false;
        } else {
            pass.setError(null);
            return true;
        }
    }

    //This function will execute when user click on Register Button
    public void registerUser(View view) {

        //Performing Validation by calling validation functions
        if(!validateName() | !validatePassword() | !validatePhoneNo() | !validateEmail() | !checkPolicy()){
            return;
        }

        //Get all the values in String
        String fullname = name.getText().toString();
        String username = email.getText().toString();
        String phoneNo = telephone.getText().toString();
        String password = pass.getText().toString();
        boolean policychecked =policy.isChecked();

        Constants.app.getEmailPassword().registerUserAsync(username, password, it -> {
            if (it.isSuccess()) {
                Log.i("EXAMPLE", "Successfully registered user.");
                showAlert("Account Created Successfully!", "Please verify your email before Login", false);

            } else {
                Log.e("EXAMPLE", "Failed to register user: " + it.getError().getErrorMessage());
                showAlert("Error Account Creation failed", "Account could not be created" + " :" +it.getError().getErrorMessage(), true);

            }
        });
       /* Intent intent = new Intent(getActivity().getApplicationContext(),VerifyPhoneNo.class);
        intent.putExtra("name",fullname);
        intent.putExtra("isChecked",policychecked);
        intent.putExtra("email",username);
        intent.putExtra("phoneNo",phoneNo);
        intent.putExtra("password",password);
        startActivity(intent);*/


    }

    private void showAlert(String title, String message, boolean error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                    // don't forget to change the line below with the names of your Activities
                    if (!error) {
                        Intent intent = getActivity().getIntent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}
